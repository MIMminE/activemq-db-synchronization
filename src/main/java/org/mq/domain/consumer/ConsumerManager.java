package org.mq.domain.consumer;

public interface ConsumerManager<T extends Consumer> {

    T createConsumer();
    T createConsumer(Object... params);

    void registerConsumer(T consumer);
}
