package org.broker.product.activemq.publisher.policy.timeslice;

import com.google.common.collect.ArrayListMultimap;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.broker.mapper.QueryMapper;
import org.broker.product.activemq.publisher.ActiveMQPublisher;
import org.broker.product.activemq.publisher.ActiveMQPublisherPolicy;
import org.broker.product.activemq.publisher.policy.timeslice.ActiveMQPublisherTimeSliceProperties.SyncInfoProperties;
import org.springframework.jms.core.JmsTemplate;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Slf4j
@RequiredArgsConstructor
public class ActiveMQPublisherTimeSlicePolicy implements ActiveMQPublisherPolicy {


    private final ActiveMQPublisherTimeSliceProperties properties;
    private final JmsTemplate jmsTemplate;
    private final QueryMapper mapper;


    @Override
    public List<ActiveMQPublisher> createPublisher() {
        List<ActiveMQPublisher> resultList = new ArrayList<>();
        List<SyncInfoProperties> syncInfo = properties.getSyncInfo();

        for (SyncInfoProperties properties : syncInfo) {
            ActiveMQPublisher activeMQPublisher = new ActiveMQPublisher(properties.getSourceTable(), properties.getTopic());

            List<List<Integer>> valueList = new ArrayList<>();
            for (int i = 0; i < properties.getThreadSize(); i++) {
                valueList.add(new ArrayList<>());
            }

            for (int i = 0; i < 10; i++) {
                int index = i % properties.getThreadSize();
                valueList.get(index).add(i);
            }

            ArrayListMultimap<Integer, Runnable> runnableMultiMap = ArrayListMultimap.create();

            for (int index = 0; index < properties.getThreadSize(); index++) {
                for (Integer value : valueList.get(index)) {
                    runnableMultiMap.put(index, createRunnable(value, properties));
                }
            }
            activeMQPublisher.setPolicyMap("runnableMap", runnableMultiMap);
            activeMQPublisher.setPolicyMap("threadSize", properties.getThreadSize());
            resultList.add(activeMQPublisher);
        }
        return resultList;
    }

    protected Runnable createRunnable(Integer value, SyncInfoProperties properties) {
        return () -> {
            List<Map<String, Object>> selectedMessages = mapper.selectTableBySyncCondition(properties.getSourceTable(), value);
            for (Map<String, Object> message : selectedMessages) {
                mapper.updateTableSyncCondition(properties.getSourceTable(), (Long) message.get("ID_FLAG"));
                Map<String, Object> senderMessage = transferSenderMessage(message);
                jmsTemplate.convertAndSend(properties.getTopic(), senderMessage);
                log.info("Send Messages {} , Destination Topic : {}", message, properties.getTopic());
            }
        };
    }

    private Map<String, Object> transferSenderMessage(Map<String, Object> message) {
        for (String key : message.keySet()) {
            Object o = message.get(key);
            message.put(key, o.toString());
        }
        message.remove("SYNC_FLAG");
        message.remove("ID_FLAG");
        return message;
    }


    @Override
    public boolean registerPublisher(List<ActiveMQPublisher> publishers) {
        for (ActiveMQPublisher publisher : publishers) {
            ArrayListMultimap<Integer, Runnable> runnableMap =
                    (ArrayListMultimap<Integer, Runnable>) publisher.getPolicyMap().get("runnableMap");
            Integer threadSize = (Integer) publisher.getPolicyMap().get("threadSize");
            ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
            ExecutorService executorService = Executors.newFixedThreadPool(threadSize);
            scheduler.scheduleAtFixedRate(() -> {
                Collection<Runnable> values = runnableMap.values();
                for (Runnable value : values) {
                    executorService.submit(value);
                }
            }, 0, 3, TimeUnit.SECONDS);
        }
        return true;
    }
}

