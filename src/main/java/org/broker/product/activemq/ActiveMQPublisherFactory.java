package org.broker.product.activemq;

import org.broker.product.PublisherFactory;
import org.broker.product.activemq.publisher.ActiveMQPublisher;

import java.util.List;

public class ActiveMQPublisherFactory implements PublisherFactory<ActiveMQServer, ActiveMQPublisher> {
    @Override
    public ActiveMQServer createServerInstance() {
        return null;
    }

    @Override
    public List<ActiveMQPublisher> createPublisher() {
        return null;
    }

    @Override
    public void registerPublisher(List<ActiveMQPublisher> publishers) {

    }
}
