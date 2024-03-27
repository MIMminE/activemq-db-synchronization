package org.broker;

import org.broker.model.BrokerServer;
import org.broker.model.Consumer;
import org.broker.product.ConsumerFactory;

import java.util.List;

public class ConsumerManager<T extends BrokerServer<?>, C extends Consumer> {

    private final ConsumerFactory<T, C> factory;

    public ConsumerManager(ConsumerFactory<T, C> factory) {
        this.factory = factory;
    }

    public T createServerInstance() {
        return factory.createServerInstance();
    }

    public List<C> createConsumer() {
        return factory.createConsumers();
    }

    public void registerConsumer(List<C> consumers){
        factory.registerConsumer(consumers);
    }
}
