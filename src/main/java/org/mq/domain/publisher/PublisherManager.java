package org.mq.domain.publisher;

public interface PublisherManager<T> {
    T createPublisher();

    T createPublisher(Object... params);

    void registerPublisher(T publisher);

}
