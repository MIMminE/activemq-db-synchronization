package org.mq.thread;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mq.jpa.publisher.PublisherEntity;
import org.mq.jpa.publisher.PublisherRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Profile("!application-test.yaml")
@Component
@RequiredArgsConstructor
@Slf4j
public class PublisherThread {

    private final PublisherRepository repository;
    private final JmsTemplate jmsTemplate;
    private final Environment env;

    @Async("applicationTaskExecutor")
    @Transactional
    public void publish(List<Integer> modIndexList) throws InterruptedException {
        for (Integer modIndex : modIndexList) {
            List<PublisherEntity> byTargetEntities = repository.findByPublishingTarget(modIndex);
            byTargetEntities.forEach(e -> {
                jmsTemplate.convertAndSend(env.getProperty("env.message-topic-name"), e);
                log.debug("publishing to [ {} ] <- {}",env.getProperty("env.message-topic-name"),e);
                e.setSyncFlag(Boolean.TRUE);
                repository.save(e);
            });
        }
    }

}
