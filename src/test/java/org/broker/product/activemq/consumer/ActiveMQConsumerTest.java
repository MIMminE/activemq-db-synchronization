package org.broker.product.activemq.consumer;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.integration.support.converter.MapMessageConverter;
import org.springframework.jms.config.MethodJmsListenerEndpoint;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;

import java.lang.reflect.Method;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("[Unit] ActiveMQConsumer")
class ActiveMQConsumerTest {

    public void testMethod() {
    }

    @DisplayName("endpoint만 주어졌을 때 ActiveMQConsumer 인스턴스 생성에 성공한다.")
    @Test
    void createActiveMQConsumerByEndpointSuccess() throws NoSuchMethodException {
        // given
        String id = "testId";
        String destination = "testDestination";
        Method method = this.getClass().getMethod("testMethod");
        ActiveMQConsumer activeMQConsumer = new ActiveMQConsumer();
        MethodJmsListenerEndpoint endpoint = new MethodJmsListenerEndpoint();

        endpoint.setId(id);
        endpoint.setBean(this);
        endpoint.setMessageHandlerMethodFactory(new DefaultMessageHandlerMethodFactory());
        endpoint.setMethod(method);
        endpoint.setDestination(destination);

        // when
        activeMQConsumer.config(endpoint);

        // then
        assertThat(activeMQConsumer.getEndpoint())
                .isInstanceOf(MethodJmsListenerEndpoint.class)
                .extracting(
                        MethodJmsListenerEndpoint::getId,
                        MethodJmsListenerEndpoint::getBean,
                        MethodJmsListenerEndpoint::getMethod,
                        MethodJmsListenerEndpoint::getDestination)
                .contains(
                        id, this, method, destination
                );
    }

    @DisplayName("잘못된 endpoint만 주어졌을 때 ActiveMQConsumer 인스턴스 생성에 실패한다.")
    @Test
    void createActiveMQConsumerByEndpointFail() throws NoSuchMethodException {
        // given
        ActiveMQConsumer activeMQConsumer = new ActiveMQConsumer();
        MethodJmsListenerEndpoint endpoint = new MethodJmsListenerEndpoint();

        // when // then
        assertThatThrownBy(() -> activeMQConsumer.config(endpoint))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("endPoint 구성이 잘못됐습니다");

    }

    @DisplayName("listenerMethod와 destination이 주어졌을 때 ActiveMQConsumer 인스턴스 생성에 성공한다.")
    @Test
    void createActiveMQConsumerByListenerMethodAndDestination() throws NoSuchMethodException {
        // given
        String destination = "testDestination";
        Method method = this.getClass().getMethod("testMethod");
        ActiveMQConsumer activeMQConsumer = new ActiveMQConsumer();

        // when
        activeMQConsumer.config(this, method, destination);

        // then

        assertThat(activeMQConsumer.getEndpoint())
                .isInstanceOf(MethodJmsListenerEndpoint.class)
                .extracting(
                        MethodJmsListenerEndpoint::getMethod,
                        MethodJmsListenerEndpoint::getDestination)
                .contains(
                        method, destination
                );
    }

    @DisplayName("listenerMethod, destination, messageHandlerMethodFactory 주어졌을 때 ActiveMQConsumer 인스턴스 생성에 성공한다.")
    @Test
    void createActiveMQConsumerByListenerMethodAndDestinationAndMethodFactory() throws NoSuchMethodException {
        // given
        String destination = "testDestination";
        Method method = this.getClass().getMethod("testMethod");
        ActiveMQConsumer activeMQConsumer = new ActiveMQConsumer();
        DefaultMessageHandlerMethodFactory messageHandlerMethodFactory = new DefaultMessageHandlerMethodFactory();
        messageHandlerMethodFactory.setMessageConverter(new MapMessageConverter());

        // when
        activeMQConsumer.config(this,method, destination, messageHandlerMethodFactory);

        // then
        assertThat(activeMQConsumer.getHandlerMethodFactory()).isEqualTo(messageHandlerMethodFactory);
        assertThat(activeMQConsumer.getEndpoint())
                .isInstanceOf(MethodJmsListenerEndpoint.class)
                .extracting(
                        MethodJmsListenerEndpoint::getMethod,
                        MethodJmsListenerEndpoint::getDestination)
                .contains(
                        method, destination
                );
    }
}