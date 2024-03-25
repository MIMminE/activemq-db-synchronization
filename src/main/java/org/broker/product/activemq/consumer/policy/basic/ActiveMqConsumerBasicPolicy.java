package org.broker.product.activemq.consumer.policy.basic;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.broker.product.activemq.consumer.ActiveMqConsumerPolicy;
import org.broker.product.activemq.consumer.ActiveMQConsumer;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ActiveMqConsumerBasicPolicy implements ActiveMqConsumerPolicy {

    private static ActiveMqConsumerBasicPolicy instance;

    public static synchronized ActiveMqConsumerBasicPolicy getInstance() {
        if (instance == null) {
            instance = new ActiveMqConsumerBasicPolicy();
        }
        return instance;
    }

    @Override
    public List<ActiveMQConsumer> createConsumer() {
        return null;
    }

    @Override
    public void registerConsumer(List<ActiveMQConsumer> consumers) {

    }
}
