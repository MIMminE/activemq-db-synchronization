package org.mq.messaging.module.consumer;

import lombok.RequiredArgsConstructor;
import lombok.Synchronized;
import lombok.extern.slf4j.Slf4j;
import org.mq.register.publisher.JobProperties.JobProperty;
import org.springframework.jms.config.MethodJmsListenerEndpoint;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;

import java.lang.reflect.Method;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
public class ConsumerGenerator {
    final private ConsumerMethod consumerMethodClass;
    final private DefaultMessageHandlerMethodFactory handlerMethodFactory;

    @Synchronized
    public MethodJmsListenerEndpoint run(JobProperty jobProperty) throws NoSuchMethodException {
        MethodJmsListenerEndpoint endpoint = new MethodJmsListenerEndpoint();
        Object bean = consumerMethodClass;
        Method method = consumerMethodClass.getClass().getMethod("listenerExecute", Map.class);

        endpoint.setId(jobProperty.getTableName() + "listener");
        endpoint.setBean(bean);
        endpoint.setMethod(method);
        endpoint.setDestination(jobProperty.getTopicName());
        endpoint.setMessageHandlerMethodFactory(handlerMethodFactory);

        return endpoint;
    }
}
