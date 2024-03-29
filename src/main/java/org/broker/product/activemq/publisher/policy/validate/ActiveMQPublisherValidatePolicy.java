package org.broker.product.activemq.publisher.policy.validate;

import lombok.RequiredArgsConstructor;
import org.broker.product.activemq.publisher.ActiveMQPublisher;
import org.broker.product.activemq.publisher.ActiveMQPublisherPolicy;

import java.util.List;

@RequiredArgsConstructor
public class ActiveMQPublisherValidatePolicy implements ActiveMQPublisherPolicy {

    private final ActiveMQPublisherValidateProperties validateProperties;

    @Override
    public List<ActiveMQPublisher> createPublisher() {
        return null;
    }

    @Override
    public void registerPublisher(List<ActiveMQPublisher> publishers) {

    }
}
