package org.broker.product.activemq.publisher;

import org.broker.model.PublisherPolicy;

import java.util.List;

public interface ActiveMQPublisherPolicy extends PublisherPolicy {
    List<ActiveMQPublisher> createPublisher();

    boolean registerPublisher(List<ActiveMQPublisher> publishers);
}
