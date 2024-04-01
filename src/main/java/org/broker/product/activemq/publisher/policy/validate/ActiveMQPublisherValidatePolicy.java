package org.broker.product.activemq.publisher.policy.validate;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.broker.product.activemq.publisher.ActiveMQPublisher;
import org.broker.product.activemq.publisher.ActiveMQPublisherPolicy;
import org.broker.product.activemq.publisher.policy.validate.ActiveMQPublisherValidateProperties.Sample;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class ActiveMQPublisherValidatePolicy implements ActiveMQPublisherPolicy {

    private final ActiveMQPublisherValidateProperties validateProperties;
    @Getter
    private final List<ActiveMQPublisher> publisherRegistry = new ArrayList<>();

    @Override
    public List<ActiveMQPublisher> createPublisher() {
        List<ActiveMQPublisher> result = new ArrayList<>();

        List<Sample> samples = validateProperties.getSample();
        for (Sample sample : samples) {
            result.add(new ActiveMQPublisher(sample.source, sample.topic));
        }
        return result;
    }

    @Override
    public boolean registerPublisher(List<ActiveMQPublisher> publishers) {
        publisherRegistry.addAll(publishers);
        return true;
    }

}
