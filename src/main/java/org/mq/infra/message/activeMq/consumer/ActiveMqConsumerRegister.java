package org.mq.infra.message.activeMq.consumer;

import org.mq.infra.message.activeMq.consumer.model.ActiveMqConsumer;
import org.mq.infra.message.activeMq.consumer.model.JobProperties;

public interface ActiveMqConsumerRegister {

    ActiveMqConsumer createConsumer(JobProperties.JobProperty jobProperty);

    void registerConsumer(ActiveMqConsumer consumer);
}
