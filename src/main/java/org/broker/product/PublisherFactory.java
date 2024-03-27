package org.broker.product;

import org.broker.model.BrokerServer;
import org.broker.model.Publisher;

import java.util.List;

public interface PublisherFactory<T extends BrokerServer<?>, P extends Publisher> {
    T createServerInstance();

    List<P> createPublisher();

    void registerPublisher(List<P> publishers);

}
