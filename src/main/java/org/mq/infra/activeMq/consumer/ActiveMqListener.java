package org.mq.infra.activeMq.consumer;

import java.util.Map;

public interface ActiveMqListener {

    public void listenerExecute(Map<String, Object> message);
}
