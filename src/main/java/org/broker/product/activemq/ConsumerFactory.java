package org.broker.product.activemq;

import org.broker.model.BrokerServer;
import org.broker.model.Consumer;

import java.util.List;

public interface ConsumerFactory<T extends BrokerServer<?>, C extends Consumer> {
    T createServerInstance();
    List<C> createConsumers();

}