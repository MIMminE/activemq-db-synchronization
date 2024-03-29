package org.broker.product.activemq;

import jakarta.jms.JMSException;
import lombok.Getter;
import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;
import org.broker.model.BrokerServer;
import org.springframework.boot.autoconfigure.jms.artemis.ArtemisProperties;

@Getter
public class ActiveMQServer implements BrokerServer <ActiveMQConnectionFactory>{

    private ActiveMQConnectionFactory connectionFactory;
    private final ActiveMQProperties properties;

    public ActiveMQServer(ActiveMQProperties properties) {

        if (properties.getUser().isBlank() || properties.getPassword().isBlank() || properties.getBrokerUrl().isBlank()){
            throw new IllegalArgumentException("User, Password, BrokerUrl은 필수값입니다.");
        }

        this.properties = properties;
        this.connectionFactory = createActiveMQConnectionFactory(this.properties);
    }

    private ActiveMQConnectionFactory createActiveMQConnectionFactory(ActiveMQProperties properties) {
        try{
            connectionFactory = new ActiveMQConnectionFactory(properties.getBrokerUrl());
        } catch (RuntimeException e){
            throw new IllegalArgumentException("Property가 올바르지 않습니다.",e);
        }

        connectionFactory.setUser(properties.getUser());
        connectionFactory.setPassword(properties.getPassword());
        return connectionFactory;
    }

    @Override
    public boolean healthCheck() {
        try {
            connectionFactory.createConnection();
        } catch (JMSException e) {
            return false;
        }
        return true;
    }

    @Override
    public ActiveMQConnectionFactory getConnection() {
        return connectionFactory;
    }
}
