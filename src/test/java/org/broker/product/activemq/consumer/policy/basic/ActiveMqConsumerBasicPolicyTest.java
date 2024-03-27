package org.broker.product.activemq.consumer.policy.basic;

import lombok.extern.slf4j.Slf4j;
import org.broker.product.activemq.consumer.ActiveMQConsumer;
import org.broker.support.IntegrationConsumerBasicSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.listener.MessageListenerContainer;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@DisplayName("[Integration] ActiveMQ Consumer Basic Policy")
class ActiveMqConsumerBasicPolicyTest extends IntegrationConsumerBasicSupport {
    @Autowired
    private ActiveMqConsumerBasicPolicy activeMqConsumerBasicPolicy;


    @Autowired
    private JmsTemplate jmsTemplate;

    @DisplayName("application.yaml 파일 설정을 읽어 Consumer 리스트를 생성에 성공한다.")
    @Test
    void createConsumer() {

        // when
        List<ActiveMQConsumer> consumers = activeMqConsumerBasicPolicy.createConsumer();

        // then
        assertThat(consumers).hasSize(2);
        assertThat(consumers)
                .extracting(consumer -> consumer.getEndpoint().getDestination())
                .containsExactlyInAnyOrder("auth_topic", "system_topic");
    }

    @DisplayName("Consumer Listener 를 ActiveMQ 서버 등록에 성공한다.")
    @Test
    void registerConsumer() {
        // given
        List<ActiveMQConsumer> consumers = activeMqConsumerBasicPolicy.createConsumer();
        activeMqConsumerBasicPolicy.registerConsumer(consumers);

        // when
        Collection<MessageListenerContainer> listenerContainers = activeMqConsumerBasicPolicy.getListenerContainers();

        // then
        assertThat(listenerContainers).hasSize(2);
    }

    @DisplayName("Consumer Listener 로 전달된 비동기 메시지를 정상적으로 처리한다.")
    @Test
    void listenerMethod() throws ExecutionException, InterruptedException {
        // given
        CompletableFuture<Map<String, Object>> future = new CompletableFuture<>();
        List<ActiveMQConsumer> consumers = activeMqConsumerBasicPolicy.createConsumer();
        activeMqConsumerBasicPolicy.registerConsumer(consumers, future);

        String authId = "identity";
        LocalDateTime authTime = LocalDateTime.now();
        boolean authResult = false;


        Map<String, Object> testMapData = new HashMap<>();
        testMapData.put("auth_id", authId);
        testMapData.put("auth_time", authTime.toString());
        testMapData.put("auth_result", authResult);

        new Thread(() -> {
            try {
                Thread.sleep(500);
                jmsTemplate.convertAndSend("auth_topic", testMapData);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();

        // when
        Map<String, Object> result = future.get();

        // then
        assertThat(result)
                .extracting("auth_id", "auth_time","auth_result")
                .contains(authId, authTime.toString(), authResult);

    }
}