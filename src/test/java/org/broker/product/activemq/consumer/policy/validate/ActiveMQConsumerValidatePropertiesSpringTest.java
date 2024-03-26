package org.broker.product.activemq.consumer.policy.validate;

import org.assertj.core.api.Assertions;
import org.broker.support.IntegrationConsumerValidateSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.broker.product.activemq.consumer.policy.validate.ActiveMQConsumerValidateProperties.Sample;

@DisplayName("스프링 통합 Consumer - Validate Policy 테스트")
class ActiveMQConsumerValidatePropertiesSpringTest extends IntegrationConsumerValidateSupport {

    @DisplayName("app.config.activemq.policy 값이 validate 일 경우 application.yaml 설정을 정상적으로 불러온다.")
    @Test
    void readProperties() {
        // given
        List<Sample> samples = properties.getSample();

        // when // then
        Assertions.assertThat(samples).hasSize(2)
                .extracting("destination")
                .contains("valid_destination1", "valid_destination2");

    }
}