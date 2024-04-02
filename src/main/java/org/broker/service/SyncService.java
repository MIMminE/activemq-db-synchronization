package org.broker.service;

import lombok.RequiredArgsConstructor;
import org.broker.ConsumerManager;
import org.broker.PublisherManager;
import org.broker.product.activemq.ActiveMQServer;
import org.broker.product.activemq.consumer.ActiveMQConsumer;
import org.broker.product.activemq.publisher.ActiveMQPublisher;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Profile("prod")
public class SyncService implements CommandLineRunner {

    private final PublisherManager<ActiveMQServer, ActiveMQPublisher> publisherManager;
    private final ConsumerManager<ActiveMQServer, ActiveMQConsumer> consumerManager;

    @Override
    public void run(String... args) throws Exception {
        List<ActiveMQPublisher> publishers = publisherManager.createPublisher();
        publisherManager.registerPublisher(publishers);

        List<ActiveMQConsumer> consumers = consumerManager.createConsumer();
        consumerManager.registerConsumer(consumers);
    }

}
