package org.broker.product.activemq.publisher;

import java.util.List;

public interface ActiveMQPublisherPolicy {
    List<ActiveMQPublisher> createPublisher();

    void registerPublisher(List<ActiveMQPublisher> publishers);
}
