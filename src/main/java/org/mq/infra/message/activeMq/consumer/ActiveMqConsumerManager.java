package org.mq.infra.message.activeMq.consumer;

import lombok.extern.slf4j.Slf4j;
import org.mq.domain.consumer.ConsumerManager;
import org.mq.infra.message.activeMq.consumer.model.ActiveMqConsumer;
import org.mq.infra.message.activeMq.consumer.model.JobProperties.JobProperty;
import org.mq.infra.message.activeMq.consumer.register.ActiveMqConsumerRegister;
import org.springframework.jms.config.JmsListenerEndpointRegistry;

@Slf4j
public class ActiveMqConsumerManager extends JmsListenerEndpointRegistry implements ConsumerManager<ActiveMqConsumer> {

    private final ActiveMqConsumerRegister consumerRegister;
    private ActiveMqConsumer consumer = null;

    public ActiveMqConsumerManager(ActiveMqConsumerRegister consumerRegister) {
        this.consumerRegister = consumerRegister;
    }

//    public void registerConsumer() {
//        super.registerListenerContainer(
//                consumer.getEndpoint(),
//                consumer.getFactory(),
//                consumer.isStartImmediately());
//    }

    @Deprecated
    @Override
    public ActiveMqConsumer createConsumer() {
        return null;
    }

    @Override
    public ActiveMqConsumer createConsumer(Object... params) {
        JobProperty jobProperty = (JobProperty) params[0];
        return consumerRegister.createConsumer(jobProperty);
    }

    @Override
    public void registerConsumer(ActiveMqConsumer consumer) {

    }
}
