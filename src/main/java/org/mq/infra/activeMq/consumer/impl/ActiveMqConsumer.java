package org.mq.infra.activeMq.consumer.impl;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.mq.domain.consumer.Consumer;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.MethodJmsListenerEndpoint;

@Builder
@Getter
@ToString
public class ActiveMqConsumer extends Consumer {
    private final MethodJmsListenerEndpoint endpoint;
    private final DefaultJmsListenerContainerFactory factory;
    private final boolean startImmediately;
}
