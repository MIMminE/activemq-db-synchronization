package org.mq.infra.activeMq.consumer.impl;

import lombok.AllArgsConstructor;
import org.mq.domain.consumer.Consumer;
import org.mq.domain.consumer.ConsumerGenerator;
import org.mq.infra.activeMq.consumer.ActiveMqListener;
import org.mq.infra.activeMq.consumer.JobProperties;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.MethodJmsListenerEndpoint;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;

import java.lang.reflect.Method;
import java.util.Map;

@AllArgsConstructor
public class ActiveMqConsumerGenerator implements ConsumerGenerator {
    private JobProperties.JobProperty jobProperty;
    private DefaultMessageHandlerMethodFactory handlerMethodFactory;
    private DefaultJmsListenerContainerFactory listenerContainerFactory;
    private ActiveMqListener activeMqListener;

    @Override
    public Consumer createConsumer() {
        MethodJmsListenerEndpoint endpoint = new MethodJmsListenerEndpoint();
        Object bean = activeMqListener;
        Method method = null;
        try {
            method = activeMqListener.getClass().getMethod("listenerExecute", Map.class);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e); // TODO :: 예외 추상화
        }

        endpoint.setId(jobProperty.getTableName() + "listener");
        endpoint.setBean(bean);
        endpoint.setMethod(method);
        endpoint.setDestination(jobProperty.getTopicName());
        endpoint.setMessageHandlerMethodFactory(handlerMethodFactory);

        return ActiveMqConsumer.builder()
                .endpoint(endpoint)
                .factory(listenerContainerFactory)
                .startImmediately(true)
                .build();
    }

}
