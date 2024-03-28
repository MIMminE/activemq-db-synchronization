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
            try {
                createSampleConsumers();
            } catch (NoSuchMethodException e) {
                throw new RuntimeException("ActiveMQConsumerValidate Sample 생성 중 오류가 발생했습니다. 잘못된 Property 가 사용되었습니다.", e);
            }
        }
        return consumers;
    }

    private void createSampleConsumers() throws NoSuchMethodException {

        for (Sample sample : properties.getSample()) {
            ActiveMQConsumer activeMQConsumer = new ActiveMQConsumer();
            activeMQConsumer.config(ActiveMQConsumer.class,ActiveMQConsumer.getSampleListenMethod(), sample.destination);
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
