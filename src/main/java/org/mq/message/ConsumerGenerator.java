package org.mq.message;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Slf4j
public class ConsumerGenerator {

    @JmsListener(destination = "sampleTopic")
    public void receive(Map<String, Object> row) throws InterruptedException {

        log.info("Consume [ {} ] -> {}", "sampleTopic", row);

    }
}
