package org.broker.product.activemq.publisher;

import java.util.List;

public interface ActiveMqPublisherPolicy {
    List<ActiveMQPublisher> createPublisher();

    void registerPublisher(List<ActiveMQPublisher> publishers);
}
