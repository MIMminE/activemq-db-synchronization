package org.mq.infra.message.activeMq.publisher.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.mq.domain.publisher.Publisher;
import org.mq.infra.message.activeMq.publisher.mapper.SqlMapper;
import org.mq.infra.message.activeMq.publisher.model.PublisherJobProperties.PublisherJobProperty;
import org.springframework.jms.core.JmsTemplate;

@Slf4j
@RequiredArgsConstructor
@ToString
@Getter
public class ActiveMqPublisher extends Publisher {
    private final JmsTemplate jmsTemplate;
    private final SqlMapper mapper;
    private final PublisherJobProperty property;
}
