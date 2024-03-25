package org.broker.product.activemq;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.autoconfigure.jms.artemis.ArtemisProperties;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.*;

@DisplayName("ActiveMQ 서버 구현체 기능 테스트")
@ExtendWith(MockitoExtension.class)
class ActiveMQServerTest {

    private final String fakeUser = "artemis";
    private final String fakePassword = "artemis";
    private final String fakeBrokerUrl = "tcp://localhost:61616";

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
        ActiveMQServerHealthChecker activeMQServerHealthChecker = Mockito.mock(ActiveMQServerHealthChecker.class);
        given(activeMQServerHealthChecker.healthCheck(
                anyString(), anyString(), anyString()
        )).willAnswer(invocationOnMock -> {
            String user = invocationOnMock.getArgument(0);
            String password = invocationOnMock.getArgument(1);
            String brokerUrl = invocationOnMock.getArgument(2);

            if (Objects.equals(user, fakeUser) && Objects.equals(password, fakePassword) && Objects.equals(brokerUrl, fakeBrokerUrl))
                return true;
            else
                return false;
        });

        ArtemisProperties properties = new ArtemisProperties();
        properties.setUser("artemis");
        properties.setPassword("artemis");
        properties.setBrokerUrl("tcp://localhost:61616");

        // when
        boolean result = activeMQServerHealthChecker.healthCheck(
                properties.getUser(),
                properties.getPassword(),
                properties.getBrokerUrl());

        // then
        assertThat(result).isTrue();
    }

    @DisplayName("서버와 연결이 불가능한 상태일 때 False를 반환한다.")
    @Test
    void healthCheckFail() {
        // given
        ActiveMQServerHealthChecker activeMQServerHealthChecker = Mockito.mock(ActiveMQServerHealthChecker.class);
        given(activeMQServerHealthChecker.healthCheck(
                anyString(), anyString(), anyString()
        )).willAnswer(invocationOnMock -> {
            String user = invocationOnMock.getArgument(0);
            String password = invocationOnMock.getArgument(1);
            String brokerUrl = invocationOnMock.getArgument(2);

            if (Objects.equals(user, fakeUser) && Objects.equals(password, fakePassword) && Objects.equals(brokerUrl, fakeBrokerUrl))
                return true;
            else
                return false;
        });

        ArtemisProperties properties = new ArtemisProperties();
        properties.setUser("art12emis");
        properties.setPassword("artemis");
        properties.setBrokerUrl("tcp://localhost:61616");

        // when
        boolean result = activeMQServerHealthChecker.healthCheck(
                properties.getUser(),
                properties.getPassword(),
                properties.getBrokerUrl()
        );

        // then
        verify(activeMQServerHealthChecker).healthCheck(properties.getUser(),properties.getPassword(),properties.getBrokerUrl());
        assertThat(result).isFalse();
    }


    interface ActiveMQServerHealthChecker {
        boolean healthCheck(String user, String password, String brokerUrl);
    }
}