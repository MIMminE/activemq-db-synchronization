package org.broker.product.activemq.consumer;

import org.broker.product.activemq.consumer.model.ActiveMQConsumer;

import java.util.List;

public class ActiveMqConsumerMultiThreadPolicy implements ActiveMqConsumerPolicy {
    @Override
    public List<ActiveMQConsumer> createConsumer() {
        return null;
    }
}
