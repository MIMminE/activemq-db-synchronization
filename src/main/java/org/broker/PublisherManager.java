package org.broker;

import org.broker.model.BrokerServer;
import org.broker.model.Publisher;
import org.broker.product.PublisherFactory;
import org.springframework.stereotype.Component;

import java.util.List;

public class PublisherManager<T extends BrokerServer<?>, P extends Publisher> {

    private PublisherFactory<T, P> factory;

    public PublisherManager(PublisherFactory<T, P> factory) {
        this.factory = factory;
    }

    public T createServerInstance() {
        return factory.createServerInstance();
    }

    public List<P> createPublisher() {
        return factory.createPublishers();
    }

    public void registerPublisher(List<P> publishers){
        factory.registerPublishers(publishers);
    }
}
