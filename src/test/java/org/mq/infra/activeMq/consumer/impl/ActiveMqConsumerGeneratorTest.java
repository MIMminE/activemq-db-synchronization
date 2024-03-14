package org.mq.infra.activeMq.consumer.impl;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mq.domain.consumer.Consumer;
import org.mq.infra.activeMq.consumer.ActiveMqListener;
import org.mq.infra.activeMq.consumer.JobProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class ActiveMqConsumerGeneratorTest {

    static private JobProperties.JobProperty property;

    @Autowired
    private DefaultJmsListenerContainerFactory defaultJmsListenerContainerFactory;

    @BeforeAll
    static void init(){
        property = new JobProperties.JobProperty();
        property.setTopicName("testTopic");
        property.setTableName("testTable");
        property.setThreadSize(10);
        property.setIntervalMillis(5000);

    }

    @Test
    void activeMQ_Consumer_생성_성공(){
        DefaultMessageHandlerMethodFactory methodFactory = new DefaultMessageHandlerMethodFactory();
        ActiveMqListener activeMqListener = new ActiveMqDefaultListener();


        ActiveMqConsumerGenerator generator = new ActiveMqConsumerGenerator(
                property,
                methodFactory,
                defaultJmsListenerContainerFactory,
                activeMqListener);

        Consumer consumer = generator.createConsumer();

        Assertions.assertThat(consumer).isInstanceOf(ActiveMqConsumer.class);
        System.out.println(consumer.toString());
    }
}