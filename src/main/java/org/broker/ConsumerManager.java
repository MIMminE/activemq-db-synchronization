package org.broker;

import org.broker.model.BrokerServer;
import org.broker.model.Consumer;
import org.broker.product.activemq.ConsumerFactory;

import java.util.List;

public class ConsumerManager<T extends BrokerServer<?>, C extends Consumer> {

    private final ConsumerFactory<T, C> executor;

    public ConsumerManager(ConsumerFactory<T, C> executor) {
        this.executor = executor;
    }

    public T createServerInstance() {
        return executor.createServerInstance();
    }

    public List<C> createConsumer() {
        return executor.createConsumers();
    }
}
