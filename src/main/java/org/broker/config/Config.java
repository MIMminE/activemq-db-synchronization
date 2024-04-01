package org.broker.config;

import org.broker.ConsumerManager;
import org.broker.PublisherManager;
import org.broker.product.activemq.ActiveMQProperties;
import org.broker.product.activemq.ActiveMQPublisherFactory;
import org.broker.product.activemq.ActiveMQServer;
import org.broker.product.activemq.publisher.ActiveMQPublisher;
import org.broker.product.activemq.publisher.ActiveMQPublisherPolicy;
import org.broker.product.activemq.publisher.policy.timeslice.ActiveMQPublisherTimeSlicePolicy;
import org.broker.product.activemq.publisher.policy.timeslice.ActiveMQPublisherTimeSliceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("prod")
@Import(ActiveMQPublisherTimeSlicePolicy.class)
public class Config {

    @Bean
    PublisherManager<ActiveMQServer, ActiveMQPublisher> publisherManager(
            ActiveMQPublisherFactory publisherFactory) {
        return new PublisherManager<>(publisherFactory);
    }

    @Bean
    ActiveMQPublisherFactory publisherFactory(
            ActiveMQPublisherTimeSlicePolicy policy,
            ActiveMQProperties properties) {
        return new ActiveMQPublisherFactory(policy, properties);
    }


//    @Bean
//    ConsumerManager consumerManager(){
//
//    }
}
