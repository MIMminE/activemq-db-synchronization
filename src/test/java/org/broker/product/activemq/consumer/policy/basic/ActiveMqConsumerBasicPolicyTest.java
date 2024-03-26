package org.broker.product.activemq.consumer.policy.basic;

import org.assertj.core.api.Assertions;
import org.broker.product.activemq.consumer.ActiveMQConsumer;
import org.broker.support.IntegrationConsumerBasicSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.util.List;


@Configuration
class config {

    @Bean
    public ActiveMqConsumerBasicPolicy activeMqConsumerBasicPolicy(ActiveMQConsumerBasicProperties properties) {
        return new ActiveMqConsumerBasicPolicy(properties);
    }
}

@Import(config.class)
class ActiveMqConsumerBasicPolicyTest extends IntegrationConsumerBasicSupport {

    @Autowired
    private ActiveMqConsumerBasicPolicy activeMqConsumerBasicPolicy;

    @DisplayName("application.yaml 파일 설정을 읽어 Consumer 리스트를 생성에 성공한다.")
    @Test
    void createConsumer() {

        // when
        List<ActiveMQConsumer> consumers = activeMqConsumerBasicPolicy.createConsumer();

        // then
        Assertions.assertThat(consumers).hasSize(2);
        Assertions.assertThat(consumers)
                .extracting(consumer -> consumer.getEndpoint().getDestination())
                .containsExactlyInAnyOrder("auth_topic","system_topic");
    }

    @DisplayName("Consumer 를 서버에 등록한다.")
    @Test
    void registerConsumer() {
        // given
        List<ActiveMQConsumer> consumers = activeMqConsumerBasicPolicy.createConsumer();

        // when
        activeMqConsumerBasicPolicy.registerConsumer(consumers, null);

        // then
    }
}