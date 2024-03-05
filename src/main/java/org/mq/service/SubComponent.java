package org.mq.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.mq.jpa.dest_log.DestLogEntity;
import org.mq.jpa.dest_log.DestRepository;
import org.mq.jpa.source_log.SourceLogEntity;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SubComponent {

    private final DestRepository destRepository;
    private ModelMapper modelMapper = new ModelMapper();

    @JmsListener(destination = "logTopic")
    public void receive(SourceLogEntity message) throws InterruptedException {

        DestLogEntity destLogEntity = modelMapper.map(message, DestLogEntity.class);
        destRepository.save(destLogEntity);
//        System.out.println("log!");
    }
}
