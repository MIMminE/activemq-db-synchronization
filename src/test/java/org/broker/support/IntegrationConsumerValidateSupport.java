package org.broker.support;

import org.broker.product.activemq.consumer.policy.validate.ActiveMQConsumerValidateProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles(profiles = "consumer-valid-test")
@SpringBootTest
public abstract class IntegrationConsumerValidateSupport {

    @Autowired
    protected ActiveMQConsumerValidateProperties properties;
}
