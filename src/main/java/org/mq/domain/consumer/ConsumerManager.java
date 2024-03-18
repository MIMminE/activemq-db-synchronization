package org.mq.domain.consumer;

import java.util.List;

public interface ConsumerManager<T extends Consumer> {

    T createConsumer();
    T createConsumer(Object... params);

    void registerConsumer(T consumer);
}
