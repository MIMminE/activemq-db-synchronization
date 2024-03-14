package org.mq.domain.consumer;

@FunctionalInterface
public interface ConsumerRegistry <T extends Consumer>{
    public void register(T consumer);

}
