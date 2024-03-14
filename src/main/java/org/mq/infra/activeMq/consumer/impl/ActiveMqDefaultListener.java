package org.mq.infra.activeMq.consumer.impl;

import lombok.extern.slf4j.Slf4j;
import org.mq.infra.activeMq.consumer.ActiveMqListener;

import java.util.Map;

@Slf4j
public class ActiveMqDefaultListener implements ActiveMqListener {
    @Override
    public void listenerExecute(Map<String, Object> message) {
        log.info("listening {}", message);
    }
}
