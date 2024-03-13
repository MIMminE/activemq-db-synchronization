package org.mq.messaging.module.consumer;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DefaultConsumerListener implements ConsumerMethod{
    @Override
    public void listenerExecute(){
        log.info("listening {}", "test!");
    }
}
