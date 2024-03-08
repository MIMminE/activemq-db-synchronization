package org.mq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
public class MqApplication{

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(MqApplication.class, args);

    }
}


