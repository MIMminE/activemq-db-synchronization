package org.mq.message;

import jakarta.jms.ConnectionFactory;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.listener.SimpleMessageListenerContainer;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class DynamicJmsListenerContainer {

    @Qualifier("jmsConnectionFactory")
    final private ConnectionFactory factory;
    @Getter
    private SimpleMessageListenerContainer container;
    final JmsTemplate jmsTemplate;

    public void init() {
        container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(factory);
        container.setPubSubDomain(true);
        container.setDestinationName("sampleTopic");
        container.setTaskExecutor(command -> {
            log.info("실행!");
        });
        container.start();
    }
}
