package org.broker.product.activemq;

import org.broker.model.BrokerServer;
import org.broker.model.Consumer;

public interface ConsumerFactory {
    BrokerServer createServerInstance();

    Consumer createConsumer();
}