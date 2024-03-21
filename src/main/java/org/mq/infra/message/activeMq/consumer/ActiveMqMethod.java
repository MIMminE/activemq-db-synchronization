package org.mq.infra.message.activeMq.consumer;

public interface ActiveMqMethod<T> {
    void listenerExecute(T param);
}
