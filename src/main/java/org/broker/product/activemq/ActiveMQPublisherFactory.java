package org.broker.product.activemq;

import lombok.Getter;
import org.broker.product.PublisherFactory;
import org.broker.product.activemq.publisher.ActiveMQPublisher;
import org.broker.product.activemq.publisher.ActiveMQPublisherPolicy;
import org.springframework.boot.autoconfigure.jms.artemis.ArtemisProperties;

import java.util.List;

public class ActiveMQPublisherFactory implements PublisherFactory<ActiveMQServer, ActiveMQPublisher> {

    @Getter
    private ActiveMQPublisherPolicy publisherPolicy;
    private final ActiveMQServer activeMQServer;

    public ActiveMQPublisherFactory(ActiveMQPublisherPolicy publisherPolicy, ActiveMQProperties properties) {
        this.publisherPolicy = publisherPolicy;
        this.activeMQServer = new ActiveMQServer(properties);
    }

    @Override
    public ActiveMQServer createServerInstance() {
        return activeMQServer;
    }

    @Override
    public List<ActiveMQPublisher> createPublishers() {
        return publisherPolicy.createPublisher();
    }

    @Override
    public void registerPublishers(List<ActiveMQPublisher> publishers) {
        publisherPolicy.registerPublisher(publishers);
    }
}
