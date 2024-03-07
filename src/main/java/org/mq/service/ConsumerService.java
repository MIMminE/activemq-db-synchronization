package org.mq.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.mq.jpa.consumer.ConsumerEntity;
import org.mq.jpa.consumer.ConsumerRepository;
import org.mq.jpa.publisher.PublisherEntity;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Profile("!test")
@Component
@RequiredArgsConstructor
@Slf4j
public class ConsumerService {

    private final ConsumerRepository destRepository;
    private ModelMapper modelMapper = new ModelMapper();
    private final Environment env;

    @JmsListener(destination = "${env.message-topic-name}")
    public void receive(PublisherEntity message) throws InterruptedException {
        ConsumerEntity destLogEntity = modelMapper.map(message, ConsumerEntity.class);
        destLogEntity.setId(null);
        log.debug("Consume [ {} ] -> {}", env.getProperty("env.message-topic-name"),destLogEntity);
        destRepository.save(destLogEntity);
    }
}
