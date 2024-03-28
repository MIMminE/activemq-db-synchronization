package org.broker.product.activemq.consumer;

import org.broker.model.ConsumerPolicy;

import java.util.List;

public interface ActiveMQConsumerPolicy extends ConsumerPolicy {
    List<ActiveMQConsumer> createConsumer();

    void registerConsumer(List<ActiveMQConsumer> consumers);
}
