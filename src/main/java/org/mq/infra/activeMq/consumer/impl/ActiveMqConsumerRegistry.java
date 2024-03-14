package org.mq.infra.activeMq.consumer.impl;

import org.mq.domain.consumer.ConsumerRegistry;
import org.springframework.jms.config.JmsListenerEndpointRegistry;

public class ActiveMqConsumerRegistry<T extends ActiveMqConsumer>
        extends JmsListenerEndpointRegistry implements ConsumerRegistry<T> {

    @Override
    public void register(T consumer) {
        super.registerListenerContainer(
                consumer.getEndpoint(),
                consumer.getFactory(),
                consumer.isStartImmediately());
    }
}
