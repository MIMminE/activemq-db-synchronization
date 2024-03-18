package org.mq.infra.message.activeMq.consumer;

import org.mq.infra.message.activeMq.consumer.model.ActiveMqConsumer;
import org.mq.infra.message.activeMq.consumer.model.ConsumerJobProperties.ConsumerJobProperty;

public interface ActiveMqConsumerRegister {

    ActiveMqConsumer createConsumer(ConsumerJobProperty jobProperty);

    void registerConsumer(ActiveMqConsumer consumer);
}
