package org.broker;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;
import org.broker.model.Consumer;
import org.broker.product.activemq.ActiveMQConsumerFactory;
import org.broker.product.activemq.ActiveMQServer;
import org.broker.product.activemq.ConsumerFactory;
import org.broker.product.activemq.consumer.ActiveMqConsumerPolicy;
import org.broker.product.activemq.consumer.ActiveMqConsumerValidatePolicy;
import org.broker.product.activemq.consumer.model.ActiveMQConsumer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.jms.artemis.ArtemisProperties;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("ConsumerManger 기능 테스트")
public class ConsumerManagerTest {

    private ArtemisProperties properties = new ArtemisProperties();

    @BeforeEach
    void setUp() {
        properties.setBrokerUrl("tcp://localhost:61616");
        properties.setUser("artemis");
        properties.setPassword("artemis");
    }

    @DisplayName("Validation ConsumerPolicy 기반 ActiveMQ Broker 서버 객체 생성에 성공한다.")
    @Test
    void createServerInstance() {
        // given
        ActiveMqConsumerPolicy consumerPolicy = new ActiveMqConsumerValidatePolicy();
        ConsumerFactory<ActiveMQServer, ActiveMQConsumer> consumerFactory = new ActiveMQConsumerFactory(consumerPolicy, properties);
        ConsumerManager<ActiveMQServer, ActiveMQConsumer> manager = new ConsumerManager<>(consumerFactory);

        // when
        ActiveMQServer activeMQServer = manager.createServerInstance();

        // then
        assertThat(activeMQServer.getConnection()).isInstanceOf(ActiveMQConnectionFactory.class);
    }

    @DisplayName("검증 정책 컨슈머 생성")
    @Test
    void createConsumer() {
        // given
        ActiveMqConsumerPolicy consumerPolicy = new ActiveMqConsumerValidatePolicy();
        ConsumerFactory<ActiveMQServer, ActiveMQConsumer> consumerFactory = new ActiveMQConsumerFactory(consumerPolicy, properties);
        ConsumerManager<ActiveMQServer, ActiveMQConsumer> manager = new ConsumerManager<>(consumerFactory);

        // when
        List<ActiveMQConsumer> consumer = manager.createConsumer();

        // then
    }

    @DisplayName("컨슈머 등록")
    @Test
    void registryConsumer() {

    }
}
