package org.broker.product.activemq;

import org.broker.model.BrokerServer;
import org.broker.model.Consumer;
import org.broker.product.activemq.consumer.ActiveMqConsumerPolicy;
import org.broker.product.activemq.consumer.model.ActiveMQConsumer;

public class ActiveMQConsumerFactory implements ConsumerFactory {

    private ActiveMqConsumerPolicy consumerPolicy;
    private ActiveMQServer activeMQServer = new ActiveMQServer(); // TODO

    public ActiveMQConsumerFactory(ActiveMqConsumerPolicy consumerPolicy) {
        this.consumerPolicy = consumerPolicy;
    }

    @Override
    public BrokerServer createServerInstance() {
        return activeMQServer;
    }

    @Override
    public Consumer createConsumer() {
        // TODO

        return new ActiveMQConsumer();
    }
}
