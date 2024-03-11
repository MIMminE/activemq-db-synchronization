package org.mq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.jms.annotation.EnableJms;

@SpringBootApplication
@EnableJms
public class MqApplication{

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(MqApplication.class, args);

    }
}


