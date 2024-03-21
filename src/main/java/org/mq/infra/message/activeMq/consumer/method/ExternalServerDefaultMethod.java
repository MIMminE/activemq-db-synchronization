package org.mq.infra.message.activeMq.consumer.method;

import lombok.extern.slf4j.Slf4j;
import org.mq.infra.message.activeMq.consumer.ActiveMqMethod;

import java.util.Map;

@Slf4j
public class ExternalServerDefaultMethod implements ActiveMqMethod<Map<String,Object>> {
    @Override
    public void listenerExecute(Map<String, Object> message) {
        log.info("listening {}", message);
    }
}
