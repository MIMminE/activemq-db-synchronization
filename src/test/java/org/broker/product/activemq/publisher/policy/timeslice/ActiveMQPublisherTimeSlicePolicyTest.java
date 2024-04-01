package org.broker.product.activemq.publisher.policy.timeslice;

import com.google.common.collect.ArrayListMultimap;
import org.broker.product.activemq.publisher.ActiveMQPublisher;
import org.broker.support.IntegrationPublisherTimeSliceSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@DisplayName("[Integration] ActiveMQPublisherTimeSlicePolicy")
class ActiveMQPublisherTimeSlicePolicyTest extends IntegrationPublisherTimeSliceSupport {

    @Test
    @DisplayName("Properties 설정을 기반으로 Publisher 생성에 성공한다.")
    void createPublisher() {

        // given
        ActiveMQPublisherTimeSlicePolicy policy = new ActiveMQPublisherTimeSlicePolicy(properties, jmsTemplate, mapper);

        // when
        List<ActiveMQPublisher> publishers = policy.createPublisher();

        // then
        ArrayListMultimap<Integer, Runnable> target1 = (ArrayListMultimap) publishers.get(0).getPolicyMap().get("runnableMap");
        assertThat(target1.keySet().size()).isEqualTo(7);

        ArrayListMultimap<Integer, Runnable> target2 = (ArrayListMultimap) publishers.get(1).getPolicyMap().get("runnableMap");
        assertThat(target2.keySet().size()).isEqualTo(3);
    }

    @Test
    void registerPublisher(){
        // given
        ActiveMQPublisherTimeSlicePolicy policy = new ActiveMQPublisherTimeSlicePolicy(properties, jmsTemplate, mapper);
        List<ActiveMQPublisher> publisher = policy.createPublisher();

        // when
        boolean result = policy.registerPublisher(publisher);
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        // then
        assertThat(result).isTrue();
    }
}