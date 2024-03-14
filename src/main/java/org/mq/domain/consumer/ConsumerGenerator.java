package org.mq.domain.consumer;

@FunctionalInterface
public interface ConsumerGenerator {
    Consumer createConsumer();
}
