package org.mq.messaging.module.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
public class DefaultConsumerListener implements ConsumerMethod{
    @Override
    public void listenerExecute(Map<String, Object> message){
        log.info("listening {}", message);
    }
}
