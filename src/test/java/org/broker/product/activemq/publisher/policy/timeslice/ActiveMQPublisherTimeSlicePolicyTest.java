package org.broker.product.activemq.publisher.policy.timeslice;

import com.google.common.collect.ArrayListMultimap;
import org.broker.product.activemq.publisher.ActiveMQPublisher;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.jms.core.JmsTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.broker.product.activemq.publisher.policy.timeslice.ActiveMQPublisherTimeSliceProperties.SyncInfoProperties;


@DisplayName("[Unit] ActiveMQPublisherTimeSlicePolicy")
class ActiveMQPublisherTimeSlicePolicyTest {

    @Test
    void createPublisher() {

        // given
        JmsTemplate mockJmsTemplate = Mockito.mock(JmsTemplate.class);
        ActiveMQPublisherTimeSliceProperties properties = new ActiveMQPublisherTimeSliceProperties();
        properties.setSyncInfo(
                new ArrayList<>(Arrays.asList(
                        new SyncInfoProperties("auth_log_tbl", "auth_topic", 7, 300),
                        new SyncInfoProperties("system_log_tbl", "system_topic", 3, 1000)
                )));

        ActiveMQPublisherTimeSlicePolicy policy = new ActiveMQPublisherTimeSlicePolicy(properties, mockJmsTemplate);

        // when
        List<ActiveMQPublisher> publishers = policy.createPublisher();

        // then
        ArrayListMultimap<Integer, Runnable> target1 = (ArrayListMultimap) publishers.get(0).getPolicyMap().get("runnableMap");
        assertThat(target1.keySet().size()).isEqualTo(7);

        ArrayListMultimap<Integer, Runnable> target2 = (ArrayListMultimap) publishers.get(1).getPolicyMap().get("runnableMap");
        assertThat(target2.keySet().size()).isEqualTo(3);

    }

    @Test
    void registerPublisher() {

    }
}