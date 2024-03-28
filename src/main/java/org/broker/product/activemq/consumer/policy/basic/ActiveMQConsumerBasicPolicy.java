package org.broker.product.activemq.consumer.policy.basic;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.broker.product.activemq.consumer.ActiveMQConsumer;
import org.broker.product.activemq.consumer.ActiveMQConsumerPolicy;
import org.broker.product.activemq.consumer.policy.basic.ActiveMQConsumerBasicProperties.SyncInfoProperties;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerEndpointRegistry;

import java.util.*;
import java.util.concurrent.*;

@Slf4j
@NoArgsConstructor
public class ActiveMQConsumerBasicPolicy extends JmsListenerEndpointRegistry implements ActiveMQConsumerPolicy {

    private ActiveMQConsumerBasicProperties properties;

    private DefaultJmsListenerContainerFactory defaultJmsListenerContainerFactory;

    private CompletableFuture<Map<String, Object>> future;


    public ActiveMQConsumerBasicPolicy(ActiveMQConsumerBasicProperties properties, DefaultJmsListenerContainerFactory defaultJmsListenerContainerFactory) {
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
                        , syncInfoProperties.topic);
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
