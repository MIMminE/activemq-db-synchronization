package org.mq.domain.consumer;

public interface ConsumerManager <T extends Consumer> {
    T createConsumer();

    void registerConsumer(T consumer);
}
