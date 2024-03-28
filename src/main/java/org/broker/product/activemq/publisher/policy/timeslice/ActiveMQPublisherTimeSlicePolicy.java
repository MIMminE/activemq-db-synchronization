package org.broker.product.activemq.publisher.policy.timeslice;

import com.google.common.collect.ArrayListMultimap;
import lombok.RequiredArgsConstructor;
import org.broker.product.activemq.publisher.ActiveMQPublisher;
import org.broker.product.activemq.publisher.ActiveMQPublisherPolicy;
import org.broker.product.activemq.publisher.policy.timeslice.ActiveMQPublisherTimeSliceProperties.SyncInfoProperties;
import org.springframework.jms.core.JmsTemplate;

import java.util.ArrayList;
import java.util.List;

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
            System.out.println(properties.getSourceTable() + "DB 조회 :" + value);
            jmsTemplate.convertAndSend(properties.getTopic(), "test");
        };
    }


    @Override
    public void registerPublisher(List<ActiveMQPublisher> publishers) {

    }
}
