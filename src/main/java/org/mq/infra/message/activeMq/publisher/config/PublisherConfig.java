package org.mq.infra.message.activeMq.publisher.config;


import lombok.RequiredArgsConstructor;
import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;
import org.springframework.boot.autoconfigure.jms.JmsAutoConfiguration;
import org.springframework.boot.autoconfigure.jms.artemis.ArtemisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessagingMessageConverter;

@Configuration
@RequiredArgsConstructor
public class PublisherConfig {

    @Bean(name = "mqConnectionFactory")
    ActiveMQConnectionFactory mqConnectionFactory() {
        ArtemisProperties properties = artemisProperties();
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(properties.getBrokerUrl());
        connectionFactory.setUser(properties.getUser());
        connectionFactory.setPassword(properties.getPassword());
        return connectionFactory;
    }

    @Bean(name = "mqJmsPlate")
    JmsTemplate mqJmsTemplate() {
        JmsTemplate jmsTemplate = new JmsTemplate(mqConnectionFactory());
        jmsTemplate.setPubSubDomain(true);
//        jmsTemplate.setMessageConverter(new MappingJackson2MessageConverter());
        return jmsTemplate;
    }

    @Bean
    ArtemisProperties artemisProperties(){
        return new ArtemisProperties();
    }
}
