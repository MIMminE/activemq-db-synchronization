package org.broker.product.activemq;

import org.broker.product.ConsumerFactory;
import org.broker.product.activemq.consumer.ActiveMqConsumerPolicy;
import org.broker.product.activemq.consumer.ActiveMQConsumer;
import org.springframework.boot.autoconfigure.jms.artemis.ArtemisProperties;

import java.util.List;

public class ActiveMQConsumerFactory implements ConsumerFactory<ActiveMQServer, ActiveMQConsumer> {

    private ActiveMqConsumerPolicy consumerPolicy;
    private ActiveMQServer activeMQServer;

    public ActiveMQConsumerFactory(ActiveMqConsumerPolicy consumerPolicy, ArtemisProperties properties) {
        this.consumerPolicy = consumerPolicy;
        this.activeMQServer = new ActiveMQServer(properties);
    }

    @Override
    public ActiveMQServer createServerInstance() {
        return activeMQServer;
    }

    @Override
    public List<ActiveMQConsumer> createConsumers() {
        List<ActiveMQConsumer> consumers = consumerPolicy.createConsumer();
        return consumers;
    }

    @Override
    public void registerConsumer(List<ActiveMQConsumer> consumers) {
        consumerPolicy.registerConsumer(consumers);
    }
}
