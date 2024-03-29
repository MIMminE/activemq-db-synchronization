package org.broker.product.activemq;

import org.assertj.core.api.Assertions;
import org.broker.product.activemq.consumer.ActiveMQConsumer;
import org.broker.product.activemq.consumer.ActiveMqConsumerPolicy;
import org.broker.product.activemq.consumer.policy.basic.ActiveMqConsumerBasicPolicy;
import org.broker.product.activemq.consumer.policy.validate.ActiveMQConsumerValidateProperties;
import org.broker.product.activemq.consumer.policy.validate.ActiveMqConsumerValidatePolicy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.autoconfigure.jms.artemis.ArtemisProperties;

import java.util.List;
import java.util.stream.Stream;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@DisplayName("[Unit] ActiveMQConsumerFactory")
class ActiveMQConsumerFactoryTest {

    static Stream<ActiveMqConsumerPolicy> activeMQConsumerPolicyProvider() {
        return Stream.of(
                new ActiveMqConsumerBasicPolicy(),
                new ActiveMqConsumerValidatePolicy(new ActiveMQConsumerValidateProperties())
        );
    }

    @DisplayName("Consumer Policy 의존성이 올바르게 주입된다.")
    @MethodSource("activeMQConsumerPolicyProvider")
    @ParameterizedTest
    void createConsumerByValidate(ActiveMqConsumerPolicy consumerPolicy) {
        // given
        ArtemisProperties properties = new ArtemisProperties();
        properties.setBrokerUrl("tcp://localhost:61616");
        properties.setUser("artemis");
        properties.setPassword("artemis");

        // when
        ActiveMQConsumerFactory factory = new ActiveMQConsumerFactory(consumerPolicy, properties);

        // then
        Assertions.assertThat(factory.getConsumerPolicy()).isSameAs(consumerPolicy);
    }


    @DisplayName("createServerInstance 메서드로 생성된 각 Policy 별 ActiveMQServer 객체를 올바르게 반환한다.")
    @MethodSource("activeMQConsumerPolicyProvider")
    @ParameterizedTest
    void createServerInstance(ActiveMqConsumerPolicy consumerPolicy) {
        // given
        ArtemisProperties properties = new ArtemisProperties();
        properties.setBrokerUrl("tcp://localhost:61616");
        properties.setUser("artemis");
        properties.setPassword("artemis");

        ActiveMQConsumerFactory factory = new ActiveMQConsumerFactory(consumerPolicy, properties);

        // when
        ActiveMQServer activeMQServer = factory.createServerInstance();

        // then
        Assertions.assertThat(activeMQServer).isInstanceOf(ActiveMQServer.class);

    }


    static Stream<Class<? extends ActiveMqConsumerPolicy>> ActiveMqConsumerPolicyClassProvider() {
        return Stream.of(
                ActiveMqConsumerBasicPolicy.class,
                ActiveMqConsumerValidatePolicy.class
        );
    }

    @DisplayName("createConsumers 메서드로 각 Policy 별 Consumer 생성 로직을 호출한다.")
    @MethodSource("ActiveMqConsumerPolicyClassProvider")
    @ParameterizedTest
    void createConsumers(Class<? extends ActiveMqConsumerPolicy> policyClass) {
        // given
        ActiveMqConsumerPolicy mockPolicy = mock(policyClass);
        ArtemisProperties properties = new ArtemisProperties();
        properties.setBrokerUrl("tcp://localhost:61616");
        properties.setUser("artemis");
        properties.setPassword("artemis");

        ActiveMQConsumerFactory factory = new ActiveMQConsumerFactory(mockPolicy, properties);

        // when
        factory.createConsumers();

        // then
        verify(mockPolicy).createConsumer();
    }

    @DisplayName("registerConsumer 메서드로 각 Policy 별 Consumer 등록 메서드를 호출한다.")
    @MethodSource("ActiveMqConsumerPolicyClassProvider")
    @ParameterizedTest
    void registerConsumer(Class<? extends ActiveMqConsumerPolicy> policyClass) {
        // given
        ActiveMqConsumerPolicy mockPolicy = mock(policyClass);
        ArtemisProperties properties = new ArtemisProperties();
        properties.setBrokerUrl("tcp://localhost:61616");
        properties.setUser("artemis");
        properties.setPassword("artemis");

        ActiveMQConsumerFactory factory = new ActiveMQConsumerFactory(mockPolicy, properties);
        List<ActiveMQConsumer> consumers = factory.createConsumers();

        // when
        factory.registerConsumer(consumers);

        // then
        verify(mockPolicy).registerConsumer(consumers);
    }


}