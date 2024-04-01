package org.broker.product.activemq.consumer.policy.validate;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.broker.product.activemq.consumer.ActiveMQConsumer;
import org.broker.product.activemq.consumer.ActiveMQConsumerPolicy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.broker.product.activemq.consumer.policy.validate.ActiveMQConsumerValidateProperties.*;

@RequiredArgsConstructor
public class ActiveMQConsumerValidatePolicy implements ActiveMQConsumerPolicy {

    private final ActiveMQConsumerValidateProperties properties;
    private List<ActiveMQConsumer> consumers = new ArrayList<>();


    public void injectConsumer(ActiveMQConsumer consumer) {
        consumers.add(consumer);
    }


    @Getter
    private Map<Long, ActiveMQConsumer> consumerRegistry = new HashMap<>();
    static Long id = 1L;

    @Override
    public List<ActiveMQConsumer> createConsumer() {
        if (consumers.isEmpty()) {
            createSampleConsumers();
        }
        return consumers;
    }

    private void createSampleConsumers(){

        for (Sample sample : properties.getSample()) {
            ActiveMQConsumer activeMQConsumer = new ActiveMQConsumer();
            try {
                activeMQConsumer.config(ActiveMQConsumer.class,ActiveMQConsumer.getSampleListenMethod(), sample.destination);
            } catch (NoSuchMethodException e) {
                throw new RuntimeException("컨슈머 생성에 실패했습니다.",e);
            }
            consumers.add(activeMQConsumer);
        }
    }

    @Override
    public void registerConsumer(List<ActiveMQConsumer> consumers) {
        for (ActiveMQConsumer consumer : consumers) {
            consumerRegistry.put(id++, consumer);
        }
    }

}
