package org.broker.product.activemq.consumer;

import lombok.Getter;
import org.broker.model.Consumer;
import org.springframework.jms.config.MethodJmsListenerEndpoint;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;
import org.springframework.messaging.handler.annotation.support.MessageHandlerMethodFactory;

import java.lang.reflect.Method;
import java.util.UUID;


@Getter
public class ActiveMQConsumer implements Consumer {

    private MethodJmsListenerEndpoint endpoint;
    private MessageHandlerMethodFactory handlerMethodFactory;

    public void config(MethodJmsListenerEndpoint endpoint) {
        if(validate(endpoint))
            throw new IllegalArgumentException("endPoint 구성이 잘못됐습니다");

        this.endpoint = endpoint;
    }

    public void config(Object been, Method listenerMethod, String destinationName) {
        this.endpoint = createEndpoint(destinationName, listenerMethod, been);
    }

    public void config(Object been, Method listenerMethod, String destinationName,
                       MessageHandlerMethodFactory messageHandlerMethodFactory) {
        this.endpoint = createEndpoint(been,listenerMethod, destinationName, messageHandlerMethodFactory);
    }

    public static Method getSampleListenMethod() throws NoSuchMethodException {
        return ActiveMQConsumer.class.getDeclaredMethod("sampleListenMethod");
    }

    private boolean validate(MethodJmsListenerEndpoint endpoint) {
        return endpoint.getId().isBlank() || endpoint.getBean() == null || endpoint.getMethod() == null || endpoint.getDestination() == null;
    }

    private MethodJmsListenerEndpoint createEndpoint(Object been,
                                                     Method listenerMethod,
                                                     String destinationName,
                                                     MessageHandlerMethodFactory messageHandlerMethodFactory) {
        MethodJmsListenerEndpoint endpoint = createEndpoint(destinationName, listenerMethod, been);
        this.handlerMethodFactory = messageHandlerMethodFactory;
        endpoint.setMessageHandlerMethodFactory(messageHandlerMethodFactory);
        return endpoint;
    }

    private MethodJmsListenerEndpoint createEndpoint(String destinationName, Method listenerMethod, Object been) {
        MethodJmsListenerEndpoint endpoint = new MethodJmsListenerEndpoint();
        endpoint.setId(UUID.randomUUID().toString().substring(4));
        endpoint.setBean(been);
        endpoint.setMethod(listenerMethod);
        endpoint.setDestination(destinationName);
        endpoint.setMessageHandlerMethodFactory(defaultMessageHandler());

        return endpoint;
    }

    private MessageHandlerMethodFactory defaultMessageHandler() {
        DefaultMessageHandlerMethodFactory methodFactory = new DefaultMessageHandlerMethodFactory();
        methodFactory.afterPropertiesSet();
        return methodFactory;
    }

    public static void sampleListenMethod(){
    }
}
