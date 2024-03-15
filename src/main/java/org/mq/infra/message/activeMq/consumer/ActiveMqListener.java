package org.mq.infra.message.activeMq.consumer;

import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Slf4j
public class ActiveMqListener {
    public void listenerExecute(Map<String, Object> message) {
        log.info("listening {}", message);
    }
}
