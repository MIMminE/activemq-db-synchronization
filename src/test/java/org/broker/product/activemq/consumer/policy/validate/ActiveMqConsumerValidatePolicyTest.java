package org.broker.product.activemq.consumer.policy.validate;

import org.broker.product.activemq.ActiveMQServer;
import org.broker.product.activemq.consumer.ActiveMQConsumer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.lang.reflect.Method;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.broker.product.activemq.consumer.policy.validate.ActiveMQConsumerValidateProperties.*;


@DisplayName("ActiveMqConsumerValidatePolicy 컴포넌트 테스트")
class ActiveMqConsumerValidatePolicyTest {

    @DisplayName("ValidatePolicy 정책의 컨슈머 생성 로직이 정상적으로 동작한다. [ 외부 주입 컨슈머를 반환 ]")
    @Test
    void createConsumerByInjectConsumer() throws NoSuchMethodException {
        // given
        ActiveMQConsumerValidateProperties validateProperties = new ActiveMQConsumerValidateProperties();
        validateProperties.setList(List.of(new Sample("sample1"), new Sample("sample2")));

        ActiveMqConsumerValidatePolicy validatePolicy = new ActiveMqConsumerValidatePolicy(validateProperties);
        ActiveMQConsumer activeMQConsumer1 = new ActiveMQConsumer();
        ActiveMQConsumer activeMQConsumer2 = new ActiveMQConsumer();

        Method method1 = this.getClass().getMethod("testMethod");
        String destination1 = "testDest1";
        activeMQConsumer1.config(method1, destination1);

        Method method2 = this.getClass().getMethod("testMethod");
        String destination2 = "testDest1";
        activeMQConsumer2.config(method2, destination2);

        validatePolicy.injectConsumer(activeMQConsumer1);
        validatePolicy.injectConsumer(activeMQConsumer2);

        // when
        List<ActiveMQConsumer> consumers = validatePolicy.createConsumer();

        // then
        assertThat(consumers)
                .hasSize(2)
                .contains(activeMQConsumer1, activeMQConsumer2);
    }

    @DisplayName("ValidatePolicy 정책의 컨슈머 생성 로직이 정상적으로 동작한다. [ 샘플 컨슈머를 반환 ]")
    @Test
    void createConsumerByDefault() {
        // given
        ActiveMQConsumerValidateProperties validateProperties = new ActiveMQConsumerValidateProperties();
        validateProperties.setList(List.of(new Sample("sample1"), new Sample("sample2")));
        ActiveMqConsumerValidatePolicy validatePolicy = new ActiveMqConsumerValidatePolicy(validateProperties);

        // when
        List<ActiveMQConsumer> consumers = validatePolicy.createConsumer();

        // then
        assertThat(consumers).hasSize(2);
        assertThat(extractProperty("endpoint.destination")
                .from(consumers))
                .containsExactly("sample1", "sample2");

    }

    @DisplayName("ValidatePolicy 정책의 컨슈머 등록 로직이 정상적으로 동작한다.")
    @Test
    void registerConsumer() throws NoSuchMethodException {
        // given
        ActiveMQServer mockServer = Mockito.mock(ActiveMQServer.class);
        ActiveMQConsumerValidateProperties validateProperties = new ActiveMQConsumerValidateProperties();
        validateProperties.setList(List.of(new Sample("sample1"), new Sample("sample2")));
        ActiveMqConsumerValidatePolicy validatePolicy = new ActiveMqConsumerValidatePolicy(validateProperties);
        ActiveMQConsumer activeMQConsumer1 = new ActiveMQConsumer();
        ActiveMQConsumer activeMQConsumer2 = new ActiveMQConsumer();

        Method method1 = this.getClass().getMethod("testMethod");
        String destination1 = "testDest1";
        activeMQConsumer1.config(method1, destination1);

        Method method2 = this.getClass().getMethod("testMethod");
        String destination2 = "testDest1";
        activeMQConsumer2.config(method2, destination2);

        validatePolicy.injectConsumer(activeMQConsumer1);
        validatePolicy.injectConsumer(activeMQConsumer2);

        List<ActiveMQConsumer> consumers = validatePolicy.createConsumer();

        // when
        validatePolicy.registerConsumer(consumers, mockServer);

        // then
        assertThat(validatePolicy.getConsumerRegistry()).hasSize(2);
        assertThat(validatePolicy.getConsumerRegistry().values())
                .contains(activeMQConsumer1, activeMQConsumer2);

    }

    public void testMethod() {
    }
}


