package org.broker.product.activemq.consumer.policy.basic;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.broker.mapper.QueryMapper;
import org.broker.product.activemq.ActiveMQServer;
import org.broker.product.activemq.consumer.ActiveMQConsumer;
import org.broker.product.activemq.consumer.ActiveMQConsumerPolicy;
import org.broker.product.activemq.consumer.policy.basic.ActiveMQConsumerBasicProperties.SyncInfoProperties;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerEndpointRegistry;

import java.util.*;
import java.util.concurrent.*;

@Slf4j
@RequiredArgsConstructor
public class ActiveMQConsumerBasicPolicy extends JmsListenerEndpointRegistry implements ActiveMQConsumerPolicy {

    private final ActiveMQConsumerBasicProperties properties;

    private final DefaultJmsListenerContainerFactory defaultJmsListenerContainerFactory;

    private CompletableFuture<Map<String, Object>> future;

    private final QueryMapper mapper;

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
                        , syncInfoProperties.getTopic());
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
        String topic = receive.get("topic").toString();
        receive.remove("topic");
        List<SyncInfoProperties> syncInfo = properties.getSyncInfo();

        for (SyncInfoProperties syncInfoProperties : syncInfo) {

            if (syncInfoProperties.getTopic().equals(topic) ){
                mapper.insertTopicMessage(syncInfoProperties.getDestinationTable(), receive);
                break;
            }
        }
    }
}
