package org.broker.product.activemq;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;
import org.broker.model.BrokerServer;
import org.springframework.boot.autoconfigure.jms.artemis.ArtemisProperties;
import org.springframework.context.annotation.Bean;

public class ActiveMQServer implements BrokerServer {

    private ActiveMQConnectionFactory connectionFactory = brokerServer();


    @Bean
    ArtemisProperties artemisProperties() {
        return new ArtemisProperties();
    }


    private ActiveMQConnectionFactory brokerServer() {
        ArtemisProperties properties = artemisProperties();


        /**
         * sample
         */

        properties.setBrokerUrl("tcp://localhost:61616");
        properties.setUser("artemis");
        properties.setPassword("artemis");

        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(properties.getBrokerUrl());
        connectionFactory.setUser(properties.getUser());
        connectionFactory.setPassword(properties.getPassword());
        return connectionFactory;
    }

    @Override
    public boolean healthCheck() {
        return true;
    }
}
