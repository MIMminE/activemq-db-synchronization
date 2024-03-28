package org.broker.product.activemq.publisher.policy.timeslice;

import org.broker.product.activemq.publisher.ActiveMQPublisher;
import org.broker.product.activemq.publisher.ActiveMQPublisherPolicy;

import java.util.List;

public class ActiveMQPublisherTimeSlicePolicy implements ActiveMQPublisherPolicy {
    @Override
    public List<ActiveMQPublisher> createPublisher() {
        return null;
    }

    @Override
    public void registerPublisher(List<ActiveMQPublisher> publishers) {

    }
}
