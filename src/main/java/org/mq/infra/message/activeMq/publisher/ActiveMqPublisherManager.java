package org.mq.infra.message.activeMq.publisher;

import lombok.AllArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.mq.domain.publisher.PublisherManager;
import org.mq.infra.message.activeMq.publisher.model.ActiveMqPublisher;
import org.mq.infra.message.activeMq.publisher.model.PublisherJobProperties.PublisherJobProperty;


@Slf4j
@AllArgsConstructor
@ToString
public class ActiveMqPublisherManager implements PublisherManager<ActiveMqPublisher> {

    private ActiveMqPublisherRegister register;

    @Deprecated
    @Override
    public ActiveMqPublisher createPublisher() {
        return null;
    }

    @Override
    public ActiveMqPublisher createPublisher(Object... params) {

        return register.createPublisher((PublisherJobProperty) params[0]);
    }

    @Override
    public void registerPublisher(ActiveMqPublisher publisher) {
        register.registerPublisher(publisher);
    }

}
