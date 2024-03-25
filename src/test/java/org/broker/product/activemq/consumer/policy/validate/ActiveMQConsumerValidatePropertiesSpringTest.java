package org.broker.product.activemq.consumer.policy.validate;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;


@ActiveProfiles(profiles = "consumer-valid-test")
@Import(ActiveMQConsumerValidateProperties.class)
@SpringBootTest
class ActiveMQConsumerValidatePropertiesSpringTest {

    @Autowired
    ActiveMQConsumerValidateProperties properties;

    @DisplayName("테스트")
    @Test
    void test() {
        // given
        List<ActiveMQConsumerValidateProperties.Sample> sample = properties.getSample();
        for (ActiveMQConsumerValidateProperties.Sample sample1 : sample) {
            System.out.println("sample1 = " + sample1);
        }

        // when

        // then
    }
}