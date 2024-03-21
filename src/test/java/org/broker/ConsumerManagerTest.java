package org.broker;

import org.assertj.core.api.Assertions;
import org.broker.model.BrokerServer;
import org.broker.model.Consumer;
import org.broker.product.activemq.ActiveMQConsumerFactory;
import org.broker.product.activemq.ConsumerFactory;
import org.broker.product.activemq.consumer.ActiveMqConsumerPolicy;
import org.broker.product.activemq.consumer.ActiveMqConsumerValidatePolicy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ConsumerManagerTest {


    @DisplayName("서버 접속")
    @Test
    void createServerInstance() {
        // given
        ActiveMqConsumerPolicy consumerPolicy = new ActiveMqConsumerValidatePolicy();
        ConsumerFactory consumerExecutor = new ActiveMQConsumerFactory(consumerPolicy);
        ConsumerManager manager = new ConsumerManager(consumerExecutor);

        // when
        BrokerServer brokerServer = manager.createServerInstance();

        // then
        Assertions.assertThat(brokerServer.healthCheck()).isTrue();
    }

    @DisplayName("검증 정책 컨슈머 생성")
    @Test
    void createConsumer() {
        // given
        ActiveMqConsumerPolicy consumerPolicy = new ActiveMqConsumerValidatePolicy();
        ConsumerFactory consumerExecutor = new ActiveMQConsumerFactory(consumerPolicy);
        ConsumerManager manager = new ConsumerManager(consumerExecutor);

        // when
        Consumer consumer = manager.createConsumer();

        // then
    }

    @DisplayName("컨슈머 등록")
    @Test
    void registryConsumer() {

    }
}
