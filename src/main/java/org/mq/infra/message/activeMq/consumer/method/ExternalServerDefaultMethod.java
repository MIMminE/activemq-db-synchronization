package org.mq.infra.message.activeMq.consumer.method;

import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Slf4j
public class ExternalServerDefaultMethod {
    public void listenerExecute(Map<String, Object> message) {
        log.info("listening {}", message);
    }
}
