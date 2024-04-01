package org.broker.product.activemq.publisher.policy.validate;

import org.assertj.core.api.Assertions;
import org.assertj.core.groups.Tuple;
import org.broker.product.activemq.publisher.ActiveMQPublisher;
import org.broker.support.IntegrationPublisherValidateSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

@DisplayName("[Integration] ActiveMQ Publisher - Validate Policy")
class ActiveMQPublisherValidatePolicyTest extends IntegrationPublisherValidateSupport{

    @DisplayName("Validate Policy 정책의 퍼블리셔 생성 로직이 정상적으로 동작한다.")
    @Test
    void createPublisher() {
        // given
        ActiveMQPublisherValidatePolicy policy = new ActiveMQPublisherValidatePolicy(properties);

        // when
        List<ActiveMQPublisher> publishers = policy.createPublisher();

        // then
        Assertions.assertThat(publishers)
                .extracting(ActiveMQPublisher::getSource, ActiveMQPublisher::getTopic)
                .contains(
                        Tuple.tuple("valid_source1", "valid_topic1"),
                        Tuple.tuple("valid_source2", "valid_topic2")
                );
    }

    @DisplayName("Validate Policy 정책의 퍼블리셔 등록 로직이 정상적으로 동작한다.")
    @Test
    void registerPublisher() {
        // given
        ActiveMQPublisherValidatePolicy policy = new ActiveMQPublisherValidatePolicy(properties);
        List<ActiveMQPublisher> publishers = policy.createPublisher();

        // when
        policy.registerPublisher(publishers);

        // then
        Assertions.assertThat(policy.getPublisherRegistry())
                .contains(publishers.get(0), publishers.get(1));
    }
}