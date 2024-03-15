package org.mq.infra.activeMq.consumer.impl;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mq.infra.message.activeMq.consumer.ActiveMqConsumer;
import org.mq.infra.message.activeMq.consumer.ActiveMqConsumerManager;
import org.mq.infra.message.activeMq.consumer.ActiveMqListener;
import org.mq.infra.message.activeMq.consumer.JobProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jms.activemq.ActiveMQConnectionDetails;
import org.springframework.boot.autoconfigure.jms.activemq.ActiveMQProperties;
import org.springframework.boot.autoconfigure.jms.artemis.ArtemisProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;
import org.springframework.test.context.ActiveProfiles;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


@SpringBootTest
@ActiveProfiles("test")
class ActiveMqConsumerGeneratorTest {
    static private JobProperties.JobProperty property;

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
        property = new JobProperties.JobProperty();
        property.setTopicName("testTopic");
        property.setTableName("testTable");
        property.setThreadSize(1);
        property.setIntervalMillis(5000);

    }

    @Test
    void activeMQ_Consumer_생성_성공() {
        DefaultMessageHandlerMethodFactory handleMethodFactory = new DefaultMessageHandlerMethodFactory();
        ActiveMqListener activeMqListener = new ActiveMqListener();
        ActiveMqConsumerManager manager = new ActiveMqConsumerManager(
                property,
                handleMethodFactory,
                defaultJmsListenerContainerFactory,
                activeMqListener
        );

        ActiveMqConsumer consumer = manager.createConsumer();
        Assertions.assertThat(consumer).isInstanceOf(ActiveMqConsumer.class);
        System.out.println(consumer.toString());

        System.out.println(details.getBrokerUrl());
        System.out.println(details.getUser());
        System.out.println(details.getPassword());

    }

    @Test
    void activeMQ_Consumer_생성_실패() {

    }


    @Test
    void activeMQ_Consumer_등록_and_메시지_수신_성공() throws InterruptedException {
        DefaultMessageHandlerMethodFactory handleMethodFactory = new DefaultMessageHandlerMethodFactory();
        handleMethodFactory.afterPropertiesSet();
        ActiveMqListener activeMqListener = new ActiveMqListener();
        ActiveMqConsumerManager manager = new ActiveMqConsumerManager(
                property,
                handleMethodFactory,
                defaultJmsListenerContainerFactory,
                activeMqListener
        );
        ActiveMqConsumer consumer = manager.createConsumer();
        manager.registerConsumer(consumer);

        Map<String, Object> testData = new HashMap<>();
        testData.put("testKey", "testData");

        jmsTemplate.convertAndSend(Objects.requireNonNull(consumer.getEndpoint().getDestination()), testData);
    }

    @Test
    void activeMQ_Consumer_등록_실패() {

    }
}