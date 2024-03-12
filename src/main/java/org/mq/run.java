package org.mq;

import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.integration.jms.dsl.Jms;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class run {
    private JmsTemplate jmsTemplate;

    @JmsListener(destination = "test")
    public void test(){
        System.out.println("test");
    }
}
