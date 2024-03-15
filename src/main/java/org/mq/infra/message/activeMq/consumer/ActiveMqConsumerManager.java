package org.mq.infra.message.activeMq.consumer;

import lombok.AllArgsConstructor;
import org.mq.domain.consumer.ConsumerManager;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerEndpointRegistry;
import org.springframework.jms.config.MethodJmsListenerEndpoint;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;

import java.lang.reflect.Method;
import java.util.Map;

@AllArgsConstructor
public class ActiveMqConsumerManager extends JmsListenerEndpointRegistry
        implements ConsumerManager<ActiveMqConsumer> {

    private JobProperties.JobProperty jobProperty;
    private DefaultMessageHandlerMethodFactory handlerMethodFactory;
    private DefaultJmsListenerContainerFactory listenerContainerFactory;
    private ActiveMqListener activeMqListener;

    @Override
    public ActiveMqConsumer createConsumer() {
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

    @Override
    public void registerConsumer(ActiveMqConsumer consumer) {
        super.registerListenerContainer(
                consumer.getEndpoint(),
                consumer.getFactory(),
                consumer.isStartImmediately());
    }

    public void registerJobProperty(JobProperties.JobProperty jobProperty){
        this.jobProperty = jobProperty;
    }
}
