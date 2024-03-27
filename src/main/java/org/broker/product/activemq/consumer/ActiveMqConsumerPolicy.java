package org.broker.product.activemq.consumer;

import org.broker.model.ConsumerPolicy;
import org.broker.product.activemq.ActiveMQServer;

import java.util.List;

public interface ActiveMqConsumerPolicy extends ConsumerPolicy {
    List<ActiveMQConsumer> createConsumer();

    void registerConsumer(List<ActiveMQConsumer> consumers);
}
