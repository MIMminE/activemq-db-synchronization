package org.mq.message;

import jakarta.jms.ConnectionFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;
import org.mq.scheduler.JobProperties;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.listener.MessageListenerContainer;
import org.springframework.jms.listener.SimpleMessageListenerContainer;
import org.springframework.jms.support.QosSettings;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.destination.DestinationResolver;
import org.springframework.stereotype.Component;

import java.util.Map;

//@Component
//@Slf4j
//@RequiredArgsConstructor

//@Profile("!dev")
//@Configuration
@Component
public class ConsumerGenerator {

    private String dest;
//    @Bean
//    public JmsListenerContainerFactory<?> myFactory(@Qualifier("jmsConnectionFactory") ConnectionFactory connectionFactory,
//                                                    DefaultJmsListenerContainerFactoryConfigurer configurer) {
//        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
//        configurer.configure(factory, connectionFactory);
//        return factory;
//    }


//    public static class DynamicListener {
//        private String destination;
//
//        public DynamicListener(String destination) {
//            this.destination = destination;
//        }
//
//        @JmsListener(destination = "#{destination}")
//        public void handleMessage(String message) {
//            System.out.println("Received message for destination " + destination + ": " + message);
//        }
//    }

    @JmsListener(destination = "sampleTopic")
    void testLis(String test) {
        System.out.println(test);
    }

}
