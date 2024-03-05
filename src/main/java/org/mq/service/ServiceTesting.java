package org.mq.service;

import jakarta.jms.JMSException;
import jakarta.jms.Message;
import lombok.RequiredArgsConstructor;
import org.mq.jpa.source_log.SourceLogEntity;
import org.mq.jpa.source_log.SourceRepository;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessagePostProcessor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ServiceTesting {

    private final SourceRepository repository;
    private final JmsTemplate jmsTemplate;

    @Async("applicationTaskExecutor")
    @Transactional
    public void update(List<Integer> modIndexList) throws InterruptedException {
        for (Integer modIndex : modIndexList) {
            List<SourceLogEntity> bySecondEntities = repository.findBySecondIsOne(modIndex);
            bySecondEntities.forEach(e -> {
                jmsTemplate.convertAndSend("logTopic", e, message -> {
                    message.setLongProperty("JMSExpiration", System.currentTimeMillis() + 2000);
                    return message;
                });
                System.out.println(Thread.currentThread().getName() + " : " + modIndex + " bySecondEntity = " + e);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
            });
        }
    }
}
