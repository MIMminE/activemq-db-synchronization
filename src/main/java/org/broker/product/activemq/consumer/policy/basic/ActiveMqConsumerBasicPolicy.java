package org.broker.product.activemq.consumer.policy.basic;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.broker.product.activemq.ActiveMQServer;
import org.broker.product.activemq.consumer.ActiveMQConsumer;
import org.broker.product.activemq.consumer.ActiveMqConsumerPolicy;
import org.broker.product.activemq.consumer.policy.basic.ActiveMQConsumerBasicProperties.SyncInfoProperties;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerEndpointRegistry;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@NoArgsConstructor
public class ActiveMqConsumerBasicPolicy extends JmsListenerEndpointRegistry implements ActiveMqConsumerPolicy {

    private ActiveMQConsumerBasicProperties properties;

    public ActiveMqConsumerBasicPolicy(ActiveMQConsumerBasicProperties properties) {
        this.properties = properties;
    }

    @Override
    public List<ActiveMQConsumer> createConsumer() {

        return createConsumerBySyncInfo();
    }

    @Override
    public void registerConsumer(List<ActiveMQConsumer> consumers, ActiveMQServer activeMQServer) {
        for (ActiveMQConsumer consumer : consumers) {
            super.registerListenerContainer(consumer.getEndpoint(),new DefaultJmsListenerContainerFactory());
        }
    }

    private List<ActiveMQConsumer> createConsumerBySyncInfo() {

        List<ActiveMQConsumer> consumers = new ArrayList<>();
        List<SyncInfoProperties> syncInfo = properties.getSyncInfo();

        for (SyncInfoProperties syncInfoProperties : syncInfo) {
            ActiveMQConsumer activeMQConsumer = new ActiveMQConsumer();
            try {
                activeMQConsumer.config(
                        this.getClass().getMethod("listenMethod")
                        ,syncInfoProperties.topicName);
                consumers.add(activeMQConsumer);
            } catch (NoSuchMethodException e) {
                throw new RuntimeException("Listener 메서드를 찾지 못했습니다.", e);
            }
        }

        return consumers;
    }

    public void listenMethod() {
        log.info("listen message");
        //TODO
    }
}
