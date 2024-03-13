package org.mq.register.consumer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mq.messaging.module.consumer.ConsumerGenerator;
import org.mq.register.publisher.JobProperties;
import org.mq.register.publisher.JobProperties.JobProperty;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerEndpointRegistry;
import org.springframework.jms.config.MethodJmsListenerEndpoint;

import java.util.List;
import java.util.Map;
import java.util.Properties;

@Slf4j
@RequiredArgsConstructor
public class ConsumerRegister implements CommandLineRunner {
    private final ConsumerGenerator generator;
    private final DefaultJmsListenerContainerFactory containerFactory;
    private final JmsListenerEndpointRegistry registry;
    private final JobProperties properties;

    public void register() throws NoSuchMethodException {
        for (JobProperty job : properties.getJobs()) {
            MethodJmsListenerEndpoint endpoint = generator.run(job);
            registry.registerListenerContainer(endpoint, containerFactory, true);
        }
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("Running CommandLineRunner Class : {}", this.getClass());
        register();
    }
}
