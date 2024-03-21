package org.broker;

import org.broker.model.BrokerServer;
import org.broker.model.Consumer;
import org.broker.product.activemq.ConsumerFactory;

public class ConsumerManager {

    private ConsumerFactory executor;

    public ConsumerManager(ConsumerFactory executor) {
        this.executor = executor;
    }

    public BrokerServer createServerInstance() {
        return executor.createServerInstance();
    }

    public Consumer createConsumer() {
        return executor.createConsumer();
    }
}
