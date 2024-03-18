package org.mq.infra.message.activeMq.publisher;

import lombok.ToString;
import org.mq.infra.message.activeMq.publisher.model.ActiveMqPublisher;
import org.mq.infra.message.activeMq.publisher.model.PublisherJobProperties.PublisherJobProperty;


public interface ActiveMqPublisherRegister {

    ActiveMqPublisher createPublisher(PublisherJobProperty jobProperty);

    void registerPublisher(ActiveMqPublisher publisher);
}
