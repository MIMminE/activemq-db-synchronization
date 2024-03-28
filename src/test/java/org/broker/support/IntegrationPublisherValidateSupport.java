package org.broker.support;

import org.broker.product.activemq.publisher.policy.validate.ActiveMQPublisherValidateProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles(profiles = "publisher-valid-test")
@SpringBootTest
//@Import(IntegrationConsumerBasicSupport.config.class)
public class IntegrationPublisherValidateSupport {

    @Autowired
    protected ActiveMQPublisherValidateProperties properties;

}
