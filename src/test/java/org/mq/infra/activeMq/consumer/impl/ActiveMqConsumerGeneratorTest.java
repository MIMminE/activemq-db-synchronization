package org.mq.infra.activeMq.consumer.impl;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mq.domain.consumer.ConsumerManager;
import org.mq.infra.message.activeMq.consumer.ActiveMqConsumerManager;
import org.mq.infra.message.activeMq.consumer.model.ActiveMqConsumer;
import org.mq.infra.message.activeMq.consumer.method.ExternalServerDefaultMethod;
import org.mq.infra.message.activeMq.consumer.model.ConsumerJobProperties.ConsumerJobProperty;
import org.mq.infra.message.activeMq.consumer.register.DefaultActiveMqConsumerRegister;
import org.mq.infra.message.activeMq.consumer.model.ConsumerJobProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jms.activemq.ActiveMQConnectionDetails;
import org.springframework.boot.autoconfigure.jms.activemq.ActiveMQProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;
import org.springframework.test.context.ActiveProfiles;

import java.util.*;


@SpringBootTest
@ActiveProfiles("test")
class ActiveMqConsumerGeneratorTest {
    static List<ConsumerJobProperty> properties = new ArrayList<>();

    @Autowired
    private DefaultJmsListenerContainerFactory defaultJmsListenerContainerFactory;

    @Autowired
    private JmsTemplate jmsTemplate;

    @Autowired
    private ActiveMQProperties activeMQProperties;

    @Autowired
    private ActiveMQConnectionDetails details;

    @BeforeAll
    static void init() {

        ConsumerJobProperty property1 = new ConsumerJobProperty();
        property1.setTopicName("testTopic");
        property1.setTableName("testTable");
        property1.setThreadSize(1);
        property1.setIntervalMillis(5000);

        ConsumerJobProperty property2 = new ConsumerJobProperty();
        property2.setTopicName("testTopic2");
        property2.setTableName("testTable2");
        property2.setThreadSize(2);
        property2.setIntervalMillis(3000);

        properties.add(property1);
        properties.add(property2);

    }

    @Test
    void activeMQ_Consumer_생성_성공() {
        ExternalServerDefaultMethod listener = new ExternalServerDefaultMethod();

        DefaultActiveMqConsumerRegister register = new DefaultActiveMqConsumerRegister(
                new DefaultMessageHandlerMethodFactory()
                , new DefaultJmsListenerContainerFactory()
                , listener);

        ConsumerManager<ActiveMqConsumer> manager = new ActiveMqConsumerManager(register);

        for (ConsumerJobProperty property : properties) {
            ActiveMqConsumer consumer = manager.createConsumer(property);
            Assertions.assertThat(consumer).isNotNull();
            System.out.println(consumer.toString());
        }
    }

    @Test
    void activeMQ_Consumer_생성_실패() {

    }


    @Test
    void activeMQ_Consumer_등록_and_메시지_수신_성공() throws InterruptedException {
        ExternalServerDefaultMethod listener = new ExternalServerDefaultMethod();

        DefaultActiveMqConsumerRegister register = new DefaultActiveMqConsumerRegister(
                new DefaultMessageHandlerMethodFactory()
                , defaultJmsListenerContainerFactory
                , listener);

        ConsumerManager<ActiveMqConsumer> manager = new ActiveMqConsumerManager(register);

        ActiveMqConsumer consumer = manager.createConsumer(properties.get(0));
        System.out.println("consumer = " + consumer);
        manager.registerConsumer(consumer);

        Map<String, Object> testData = new HashMap<>();
        testData.put("testKey", "testData");

        jmsTemplate.convertAndSend(Objects.requireNonNull(consumer.getEndpoint().getDestination()), testData);

    }

    @Test
    void activeMQ_Consumer_등록_실패() {

    }
}