package org.broker.product.activemq.consumer.policy.basic;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.broker.product.activemq.ActiveMQServer;
import org.broker.product.activemq.consumer.ActiveMQConsumer;
import org.broker.product.activemq.consumer.ActiveMqConsumerPolicy;
import org.broker.product.activemq.consumer.policy.basic.ActiveMQConsumerBasicProperties.SyncInfoProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerEndpointRegistry;
import org.springframework.jms.listener.MessageListenerContainer;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.*;
import java.util.function.Function;
import java.util.function.Supplier;

@Slf4j
@NoArgsConstructor
public class ActiveMqConsumerBasicPolicy extends JmsListenerEndpointRegistry implements ActiveMqConsumerPolicy {

    private ActiveMQConsumerBasicProperties properties;

    private DefaultJmsListenerContainerFactory defaultJmsListenerContainerFactory;

    CompletableFuture<Map<String, Object>> future;


    public ActiveMqConsumerBasicPolicy(ActiveMQConsumerBasicProperties properties, DefaultJmsListenerContainerFactory defaultJmsListenerContainerFactory) {
        this.defaultJmsListenerContainerFactory = defaultJmsListenerContainerFactory;
        this.properties = properties;
    }

    @Override
    public List<ActiveMQConsumer> createConsumer() {

        return createConsumerBySyncInfo();
    }

    @Override
    public void registerConsumer(List<ActiveMQConsumer> consumers) {
        for (ActiveMQConsumer consumer : consumers) {
            super.registerListenerContainer(consumer.getEndpoint(), defaultJmsListenerContainerFactory, true);
        }
    }

    public void registerConsumer(List<ActiveMQConsumer> consumers, CompletableFuture<Map<String, Object>> future) {

        this.future = future;

        for (ActiveMQConsumer consumer : consumers) {
            super.registerListenerContainer(consumer.getEndpoint(), defaultJmsListenerContainerFactory, true);
        }
    }

    private List<ActiveMQConsumer> createConsumerBySyncInfo() {

        List<ActiveMQConsumer> consumers = new ArrayList<>();
        List<SyncInfoProperties> syncInfo = properties.getSyncInfo();

        for (SyncInfoProperties syncInfoProperties : syncInfo) {
            ActiveMQConsumer activeMQConsumer = new ActiveMQConsumer();
            try {
                activeMQConsumer.config(
                        this,
                        this.getClass().getMethod("listenMethod", Map.class)
                        , syncInfoProperties.topicName);
                consumers.add(activeMQConsumer);
            } catch (NoSuchMethodException e) {
                throw new RuntimeException("Listener 메서드를 찾지 못했습니다.", e);
            }
        }

        return consumers;
    }

    public void listenMethod(Map<String, Object> receive) {
        if (future!=null){
            future.complete(receive);
        }
        //TODO
    }
}
