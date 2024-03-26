package org.broker.support;

import org.broker.product.activemq.consumer.policy.basic.ActiveMQConsumerBasicProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles(profiles = "consumer-basic-test")
@Import(ActiveMQConsumerBasicProperties.class)
@SpringBootTest
public abstract class IntegrationConsumerBasicSupport {

    @Autowired
    protected ActiveMQConsumerBasicProperties properties;

}
