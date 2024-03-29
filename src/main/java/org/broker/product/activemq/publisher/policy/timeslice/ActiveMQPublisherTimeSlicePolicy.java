package org.broker.product.activemq.publisher.policy.timeslice;

import com.google.common.collect.ArrayListMultimap;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.broker.product.activemq.publisher.ActiveMQPublisher;
import org.broker.product.activemq.publisher.ActiveMQPublisherPolicy;
import org.broker.product.activemq.publisher.policy.timeslice.ActiveMQPublisherTimeSliceProperties.SyncInfoProperties;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.*;

@Slf4j
@RequiredArgsConstructor
public class ActiveMQPublisherTimeSlicePolicy implements ActiveMQPublisherPolicy {

    private final ActiveMQPublisherTimeSliceProperties properties;
    private final JmsTemplate jmsTemplate;


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
            resultList.add(activeMQPublisher);
        }
        return resultList;
    }

    private Runnable createRunnable(Integer value, SyncInfoProperties properties) {
        return () -> {
            log.info(properties.getSourceTable() + "DB 조회 :" + value);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            jmsTemplate.convertAndSend(properties.getTopic(), "test");
        };
    }

    @Override
    public void registerPublisher(List<ActiveMQPublisher> publishers) {
        for (ActiveMQPublisher publisher : publishers) {
            ArrayListMultimap<Integer, Runnable> runnableMap =
                    (ArrayListMultimap<Integer, Runnable>) publisher.getPolicyMap().get("runnableMap");
            ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
            ExecutorService executorService = Executors.newFixedThreadPool(5);
            scheduler.scheduleAtFixedRate(()->{
                Collection<Runnable> values = runnableMap.values();
                for (Runnable value : values) {
                    executorService.submit(value);
                }
            },0, 3, TimeUnit.SECONDS);
        }
    }
}
