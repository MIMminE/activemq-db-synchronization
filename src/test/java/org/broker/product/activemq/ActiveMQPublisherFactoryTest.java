package org.broker.product.activemq;

import org.assertj.core.api.Assertions;
import org.broker.mapper.QueryMapper;
import org.broker.product.activemq.publisher.ActiveMQPublisher;
import org.broker.product.activemq.publisher.ActiveMQPublisherPolicy;
import org.broker.product.activemq.publisher.policy.timeslice.ActiveMQPublisherTimeSlicePolicy;
import org.broker.product.activemq.publisher.policy.timeslice.ActiveMQPublisherTimeSliceProperties;
import org.broker.product.activemq.publisher.policy.validate.ActiveMQPublisherValidatePolicy;
import org.broker.product.activemq.publisher.policy.validate.ActiveMQPublisherValidateProperties;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import org.springframework.jms.core.JmsTemplate;

import java.util.List;
import java.util.stream.Stream;

@DisplayName("[Unit] ActiveMQPublisherFactory")
class ActiveMQPublisherFactoryTest {

    private static Stream<ActiveMQPublisherPolicy> providerPublisherPolicy() {
        ActiveMQPublisherTimeSliceProperties mockTimeSliceProperties = Mockito.mock(ActiveMQPublisherTimeSliceProperties.class);
        ActiveMQPublisherValidateProperties mockValidateProperties = Mockito.mock(ActiveMQPublisherValidateProperties.class);
        JmsTemplate mockJmsTemplate = Mockito.mock(JmsTemplate.class);
        QueryMapper queryMapper = Mockito.mock(QueryMapper.class);

        return Stream.of(
                new ActiveMQPublisherTimeSlicePolicy(mockTimeSliceProperties, mockJmsTemplate, queryMapper),
                new ActiveMQPublisherValidatePolicy(mockValidateProperties)
        );
    }

    @DisplayName("Publisher Policy 의존성이 올바르게 주입된다.")
    @MethodSource("providerPublisherPolicy")
    @ParameterizedTest
    void createPublisherFactory(ActiveMQPublisherPolicy publisherPolicy) {

        // given
        ActiveMQProperties properties = new ActiveMQProperties();
        properties.setBrokerUrl("tcp://localhost:61616");
        properties.setUser("artemis");
        properties.setPassword("artemis");

        // when
        ActiveMQPublisherFactory publisherFactory = new ActiveMQPublisherFactory(publisherPolicy, properties);

        // then
        Assertions.assertThat(publisherFactory.getPublisherPolicy()).isSameAs(publisherPolicy);
    }

    @DisplayName("createServerInstance 메서드로 생성된 각 Policy 별 ActiveMQServer 객체를 올바르게 반환한다.")
    @MethodSource("providerPublisherPolicy")
    @ParameterizedTest
    void createServerInstance(ActiveMQPublisherPolicy publisherPolicy) {
        // given
        ActiveMQProperties properties = new ActiveMQProperties();
        properties.setBrokerUrl("tcp://localhost:61616");
        properties.setUser("artemis");
        properties.setPassword("artemis");

        ActiveMQPublisherFactory publisherFactory = new ActiveMQPublisherFactory(publisherPolicy, properties);

        // when
        ActiveMQServer activeMQServer = publisherFactory.createServerInstance();

        // then
        Assertions.assertThat(activeMQServer).isInstanceOf(ActiveMQServer.class);
    }


    static Stream<ActiveMQPublisherPolicy> provideMockPublisherPolicy() {
        return Stream.of(
                Mockito.mock(ActiveMQPublisherTimeSlicePolicy.class),
                Mockito.mock(ActiveMQPublisherValidatePolicy.class)
        );
    }

    @DisplayName("createPublishers 메서드로 각 Policy 별 Publisher 생성 로직을 호출한다.")
    @MethodSource("provideMockPublisherPolicy")
    @ParameterizedTest
    void createPublishers(ActiveMQPublisherPolicy publisherPolicy) {
        // given
        ActiveMQProperties properties = new ActiveMQProperties();
        properties.setBrokerUrl("tcp://localhost:61616");
        properties.setUser("artemis");
        properties.setPassword("artemis");

        ActiveMQPublisherFactory publisherFactory = new ActiveMQPublisherFactory(publisherPolicy, properties);

        // when
        publisherFactory.createPublishers();

        // then
        Mockito.verify(publisherPolicy).createPublisher();
    }

    @DisplayName("registerPublishers 메서드로 각 Policy 별 Publisher 등록 로직을 호출한다.")
    @MethodSource("provideMockPublisherPolicy")
    @ParameterizedTest
    void registerPublishers(ActiveMQPublisherPolicy publisherPolicy) {
        // given
        ActiveMQProperties properties = new ActiveMQProperties();
        properties.setBrokerUrl("tcp://localhost:61616");
        properties.setUser("artemis");
        properties.setPassword("artemis");

        ActiveMQPublisherFactory publisherFactory = new ActiveMQPublisherFactory(publisherPolicy, properties);
        List<ActiveMQPublisher> publishers = publisherFactory.createPublishers();

        // when
        publisherFactory.registerPublishers(publishers);

        // then
        Mockito.verify(publisherPolicy).registerPublisher(publishers);
    }
}