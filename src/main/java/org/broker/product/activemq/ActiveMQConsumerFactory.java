package org.broker.product.activemq;

import lombok.Getter;
import org.broker.product.ConsumerFactory;
import org.broker.product.activemq.consumer.ActiveMQConsumer;
import org.broker.product.activemq.consumer.ActiveMQConsumerPolicy;

import java.util.List;

public class ActiveMQConsumerFactory implements ConsumerFactory<ActiveMQServer, ActiveMQConsumer> {

    @Getter
    private ActiveMQConsumerPolicy consumerPolicy;
    private final ActiveMQServer activeMQServer;

    public ActiveMQConsumerFactory(ActiveMQConsumerPolicy consumerPolicy, ActiveMQProperties properties) {
        this.consumerPolicy = consumerPolicy;
        this.activeMQServer = new ActiveMQServer(properties);
    }

    @Override
    public ActiveMQServer createServerInstance() {
        return activeMQServer;
    }

    @Override
    public List<ActiveMQConsumer> createConsumers() {
        return consumerPolicy.createConsumer();
    }

    @Override
    public void registerConsumer(List<ActiveMQConsumer> consumers) {
        consumerPolicy.registerConsumer(consumers);
    }
}
