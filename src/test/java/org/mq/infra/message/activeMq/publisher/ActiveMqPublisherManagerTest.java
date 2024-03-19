package org.mq.infra.message.activeMq.publisher;

import jakarta.jms.ConnectionFactory;
import jakarta.jms.JMSException;
import jakarta.jms.Message;
import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mq.infra.message.activeMq.publisher.mapper.SqlMapper;
import org.mq.infra.message.activeMq.publisher.model.ActiveMqPublisher;
import org.mq.infra.message.activeMq.publisher.model.PublisherJobProperties.PublisherJobProperty;
import org.mq.infra.message.activeMq.publisher.register.MultiThreadActiveMqPublisherRegister;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.test.context.ActiveProfiles;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@SpringBootTest
class ActiveMqPublisherManagerTest {

    @Autowired
    private ActiveMQConnectionFactory mqConnectionFactory;

    @Autowired
    @Qualifier("mqJmsPlate")
    private JmsTemplate jmsTemplate;

    @Autowired
    private SqlMapper mapper;

    static List<PublisherJobProperty> properties = new ArrayList<>();

    @BeforeAll
    static void init() {

        PublisherJobProperty property1 = new PublisherJobProperty();
        property1.setTopicName("sampleTopic");
        property1.setTableName("authlog");
        property1.setThreadSize(5);
        property1.setIntervalMillis(2000);

        PublisherJobProperty property2 = new PublisherJobProperty();
        property2.setTopicName("testTopic22");
        property2.setTableName("testtbl");
        property2.setThreadSize(2);
        property2.setIntervalMillis(1000);

        properties.add(property1);
        properties.add(property2);

    }


    @Test
    void 단일_스레드_Publisher_생성_성공() throws InterruptedException, SQLException {

        ActiveMqPublisherRegister register = new MultiThreadActiveMqPublisherRegister(jmsTemplate, mapper);

        ActiveMqPublisherManager publisherManager = new ActiveMqPublisherManager(register);

        new Thread(() -> {
            while (true) {
                Message receive = jmsTemplate.receive("sampleTopic");
                try {
                    System.out.println(receive.getBody(Map.class));
                } catch (JMSException e) {
                    throw new RuntimeException(e);
                }
            }

        }).start();

        for (PublisherJobProperty property : properties) {
            ActiveMqPublisher publisher = publisherManager.createPublisher(property);

            log.info(publisher.toString());
            publisherManager.registerPublisher(publisher);
        }
        Thread.sleep(20000);
    }

    @Test
    void 단일_스레드_publisher_생성_실패() {

    }


    @Test
    void 단일_스레드_Publisher_메시지_전송_성공() {

    }

    @Test
    void 단일_스레드_Publisher_메시지_전송_실패() {

    }


    @Test
    void 멀티_스레드_Publisher_생성_성공() {

    }

    @Test
    void 멀티_스레드_publisher_생성_실패() {

    }


    @Test
    void 멀티_스레드_Publisher_메시지_전송_성공() {

    }

    @Test
    void 멀티_스레드_Publisher_메시지_전송_실패() {

    }
}