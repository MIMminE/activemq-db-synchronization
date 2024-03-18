package org.mq.infra.message.activeMq.consumer.register;

import org.mq.infra.message.activeMq.consumer.model.ActiveMqConsumer;
import org.mq.infra.message.activeMq.consumer.method.ExternalServerDefaultMethod;
import org.mq.infra.message.activeMq.consumer.model.JobProperties.JobProperty;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.MethodJmsListenerEndpoint;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;

import java.lang.reflect.Method;
import java.util.Map;

public class ActiveMqConsumerRegister {

    private final DefaultMessageHandlerMethodFactory methodFactory;
    private final DefaultJmsListenerContainerFactory listenerContainerFactory;
    private final ExternalServerDefaultMethod activeMqListener;

    public ActiveMqConsumerRegister(
            DefaultMessageHandlerMethodFactory methodFactory,
            DefaultJmsListenerContainerFactory listenerContainerFactory,
            ExternalServerDefaultMethod activeMqListener) {
        this.methodFactory = methodFactory;
        this.listenerContainerFactory = listenerContainerFactory;
        this.activeMqListener = activeMqListener;
    }

    public void registerConsumer() {

    }

    public ActiveMqConsumer createConsumer(JobProperty jobProperty) {

        MethodJmsListenerEndpoint endpoint = new MethodJmsListenerEndpoint();
        Method method = null;
        try {
            method = activeMqListener.getClass().getMethod("listenerExecute", Map.class);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e); // TODO :: 예외 추상화
        }

        endpoint.setId(jobProperty.getTableName() + "listener");
        endpoint.setBean(activeMqListener);
        endpoint.setMethod(method);
        endpoint.setDestination(jobProperty.getTopicName());
        endpoint.setMessageHandlerMethodFactory(methodFactory);

        return ActiveMqConsumer.builder()
                .endpoint(endpoint)
                .factory(listenerContainerFactory)
                .startImmediately(true)
                .build();
    }
}
