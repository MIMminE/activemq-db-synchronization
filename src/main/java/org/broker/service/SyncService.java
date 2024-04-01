package org.broker.service;

import lombok.RequiredArgsConstructor;
import org.broker.PublisherManager;
import org.broker.product.activemq.ActiveMQServer;
import org.broker.product.activemq.publisher.ActiveMQPublisher;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SyncService implements CommandLineRunner {

    private final PublisherManager<ActiveMQServer, ActiveMQPublisher> publisherManager;

    @Override
    public void run(String... args) throws Exception {
        List<ActiveMQPublisher> publishers = publisherManager.createPublisher();
        publisherManager.registerPublisher(publishers);

    }
}
