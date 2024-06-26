package org.broker.support;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;
import org.broker.product.activemq.ActiveMQServer;
import org.broker.product.activemq.consumer.policy.basic.ActiveMQConsumerBasicProperties;
import org.broker.product.activemq.consumer.policy.basic.ActiveMqConsumerBasicPolicy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jms.artemis.ArtemisProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles(profiles = "consumer-basic-test")
@SpringBootTest
@Import(IntegrationConsumerBasicSupport.config.class)
public abstract class IntegrationConsumerBasicSupport {

    @Autowired
    protected ActiveMQConsumerBasicProperties properties;

    static class config {
        @Bean
        public ActiveMQServer activeMQServer() {
            ArtemisProperties artemisProperties = new ArtemisProperties();
            artemisProperties.setUser("artemis");
            artemisProperties.setPassword("artemis");
            artemisProperties.setBrokerUrl("tcp://localhost:61616");

            return new ActiveMQServer(artemisProperties);
        }

        @Bean
        public ActiveMqConsumerBasicPolicy activeMqConsumerBasicPolicy(ActiveMQConsumerBasicProperties properties,
                                                                       DefaultJmsListenerContainerFactory defaultJmsListenerContainerFactory,
                                                                       ActiveMQServer activeMQServer) {

            ActiveMQConnectionFactory connectionFactory = activeMQServer.getConnectionFactory();

            defaultJmsListenerContainerFactory.setPubSubDomain(true);
            defaultJmsListenerContainerFactory.setConnectionFactory(connectionFactory);
            return new ActiveMqConsumerBasicPolicy(properties, defaultJmsListenerContainerFactory);
        }

        @Bean
        public JmsTemplate jmsTemplate(ActiveMQServer activeMQServer) {
            JmsTemplate jmsTemplate = new JmsTemplate(activeMQServer.getConnectionFactory());
            jmsTemplate.setPubSubDomain(true);
            return jmsTemplate;
        }
    }
}
