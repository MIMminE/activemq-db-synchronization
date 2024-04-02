package org.broker.config;

import lombok.RequiredArgsConstructor;
import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;
import org.broker.ConsumerManager;
import org.broker.PublisherManager;
import org.broker.mapper.QueryMapper;
import org.broker.product.activemq.ActiveMQConsumerFactory;
import org.broker.product.activemq.ActiveMQProperties;
import org.broker.product.activemq.ActiveMQPublisherFactory;
import org.broker.product.activemq.ActiveMQServer;
import org.broker.product.activemq.consumer.ActiveMQConsumer;
import org.broker.product.activemq.consumer.policy.basic.ActiveMQConsumerBasicPolicy;
import org.broker.product.activemq.consumer.policy.basic.ActiveMQConsumerBasicProperties;
import org.broker.product.activemq.publisher.ActiveMQPublisher;
import org.broker.product.activemq.publisher.policy.timeslice.ActiveMQPublisherTimeSlicePolicy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;

@Configuration
@Profile("prod")
@RequiredArgsConstructor
@Import({ActiveMQPublisherTimeSlicePolicy.class})
public class Config {

    private final ActiveMQProperties properties;
    private final QueryMapper mapper;

    @Bean
    PublisherManager<ActiveMQServer, ActiveMQPublisher> publisherManager(
            ActiveMQPublisherFactory publisherFactory) {
        return new PublisherManager<>(publisherFactory);
    }

    @Bean
    ActiveMQPublisherFactory publisherFactory(
            ActiveMQPublisherTimeSlicePolicy policy
            ) {
        return new ActiveMQPublisherFactory(policy, properties);
    }

    @Bean
    ConsumerManager<ActiveMQServer, ActiveMQConsumer> consumerManager(
            ActiveMQConsumerFactory consumerFactory) {
        return new ConsumerManager<>(consumerFactory);
    }

    @Bean
    ActiveMQConsumerFactory consumerFactory(
            ActiveMQConsumerBasicPolicy policy) {

        return new ActiveMQConsumerFactory(policy, properties);
    }

    @Bean
    public ActiveMQConsumerBasicPolicy activeMqConsumerBasicPolicy(ActiveMQConsumerBasicProperties properties,
                                                                   DefaultJmsListenerContainerFactory defaultJmsListenerContainerFactory,
                                                                   ActiveMQServer activeMQServer) {

        ActiveMQConnectionFactory connectionFactory = activeMQServer.getConnectionFactory();

        defaultJmsListenerContainerFactory.setPubSubDomain(true);
        defaultJmsListenerContainerFactory.setConnectionFactory(connectionFactory);
        return new ActiveMQConsumerBasicPolicy(properties, defaultJmsListenerContainerFactory, mapper);
    }

    @Bean
    public JmsTemplate jmsTemplate(ActiveMQServer activeMQServer) {
        JmsTemplate jmsTemplate = new JmsTemplate(activeMQServer.getConnectionFactory());
        jmsTemplate.setPubSubDomain(true);
        return jmsTemplate;
    }

    @Bean
    public ActiveMQServer activeMQServer() {

        return new ActiveMQServer(properties);
    }
}
