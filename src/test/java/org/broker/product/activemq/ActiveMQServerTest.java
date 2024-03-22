package org.broker.product.activemq;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.jms.artemis.ArtemisProperties;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

// TODO Mock 객체 사용하여 수정할 것
@DisplayName("ActiveMQ 서버 구현체 기능 테스트")
class ActiveMQServerTest {

    @DisplayName("정상적인 properties 인스턴스가 들어오면 커넥션 팩토리 생성에 성공한다.")
    @Test
    void createActiveMQConnectionFactorySuccess() {
        // given
        ArtemisProperties properties = new ArtemisProperties();
        properties.setUser("testUser");
        properties.setPassword("testPassword");
        properties.setBrokerUrl("tcp://localhost:61616");

        // when
        ActiveMQServer activeMQServer = new ActiveMQServer(properties);

        // then
        assertThat(activeMQServer.getConnection())
                .isInstanceOf(ActiveMQConnectionFactory.class)
                .extracting(ActiveMQConnectionFactory::getUser, ActiveMQConnectionFactory::getPassword)
                .contains(properties.getUser(), properties.getPassword());
        assertThat(activeMQServer.getProperties().getBrokerUrl()).isEqualTo(properties.getBrokerUrl());
    }

    @DisplayName("비정상적인 properties 인스턴스가 들어오면 커넥션 팩토리 생성에 실패한다.")
    @Test
    void createActiveMQConnectionFactoryFail() {
        // given
        ArtemisProperties properties = new ArtemisProperties();
        properties.setUser("testUser");
        properties.setPassword("testPassword");

        // when // then
        assertThatThrownBy(() -> new ActiveMQServer(properties))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Property가 올바르지 않습니다.");
    }

    @DisplayName("서버와 연결이 가능한 상태일 때 True를 반환한다.")
    @Test
    void healthCheckSuccess() {
        // given
        ArtemisProperties properties = new ArtemisProperties();
        properties.setUser("artemis");
        properties.setPassword("artemis");
        properties.setBrokerUrl("tcp://localhost:61616");
        ActiveMQServer activeMQServer = new ActiveMQServer(properties);

        // when // then
        assertThat(activeMQServer.healthCheck()).isTrue();
    }

    @DisplayName("서버와 연결이 불가능한 상태일 때 False를 반환한다.")
    @Test
    void healthCheckFail() {
        // given
        ArtemisProperties properties = new ArtemisProperties();
        properties.setUser("art2emis");
        properties.setPassword("artemis");
        properties.setBrokerUrl("tcp://localhost:61616");
        ActiveMQServer activeMQServer = new ActiveMQServer(properties);

        // when // then
        assertThat(activeMQServer.healthCheck()).isFalse();
    }
}