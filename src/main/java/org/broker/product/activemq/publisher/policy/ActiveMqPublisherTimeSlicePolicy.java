package org.broker.product.activemq.publisher.policy;

import org.broker.product.activemq.publisher.ActiveMQPublisher;
import org.broker.product.activemq.publisher.ActiveMqPublisherPolicy;

import java.util.List;

public class ActiveMqPublisherTimeSlicePolicy implements ActiveMqPublisherPolicy {
    @Override
    public List<ActiveMQPublisher> createPublisher() {
        return null;
    }

    @Override
    public void registerPublisher(List<ActiveMQPublisher> publishers) {

    }
}
