package org.mq.messaging.module.consumer;

import org.springframework.messaging.handler.annotation.Payload;

import java.util.Map;

public interface ConsumerMethod {
    public void listenerExecute(Map<String, Object> message);
}
