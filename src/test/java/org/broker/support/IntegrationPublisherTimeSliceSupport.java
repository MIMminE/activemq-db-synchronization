package org.broker.support;

import org.broker.product.activemq.publisher.policy.timeslice.ActiveMQPublisherTimeSliceProperties;
import org.broker.product.activemq.publisher.policy.validate.ActiveMQPublisherValidateProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles(profiles = "publisher-time-slice-test")
@SpringBootTest
//@Import(IntegrationConsumerBasicSupport.config.class)
public class IntegrationPublisherTimeSliceSupport {

    @Autowired
    protected ActiveMQPublisherTimeSliceProperties properties;

}
