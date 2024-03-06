package org.mq.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.mq.jpa.consumer.ConsumerEntity;
import org.mq.jpa.consumer.ConsumerRepository;
import org.mq.jpa.publisher.PublisherEntity;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ConsumerService {

    private final ConsumerRepository destRepository;
    private ModelMapper modelMapper = new ModelMapper();

    @JmsListener(destination = "${env.message-topic-name}")
    public void receive(PublisherEntity message) throws InterruptedException {

        ConsumerEntity destLogEntity = modelMapper.map(message, ConsumerEntity.class);
        destRepository.save(destLogEntity);
    }
}
