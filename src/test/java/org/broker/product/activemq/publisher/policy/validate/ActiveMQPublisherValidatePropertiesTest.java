package org.broker.product.activemq.publisher.policy.validate;

import org.assertj.core.api.Assertions;
import org.broker.support.IntegrationPublisherValidateSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.broker.product.activemq.publisher.policy.validate.ActiveMQPublisherValidateProperties.Sample;

@DisplayName("[Integration] ActiveMQ Publisher Validate Properties")
class ActiveMQPublisherValidatePropertiesTest extends IntegrationPublisherValidateSupport {

    @DisplayName("app.config.activemq.publisher.validate 설정이 존재할 경우 ValidatePolicy 설정을 정상적으로 불러온다.")
    @Test
    void readProperties() {
        // given
        List<Sample> sample = properties.getSample();

        // when // then
        Assertions.assertThat(sample).hasSize(2)
                .extracting("source")
                .contains("valid_source1", "valid_source2");
    }
}