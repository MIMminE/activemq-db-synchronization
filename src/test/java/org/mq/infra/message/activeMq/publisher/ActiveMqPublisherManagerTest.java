package org.mq.infra.message.activeMq.publisher;

import jakarta.jms.ConnectionFactory;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mq.infra.message.activeMq.publisher.mapper.SqlMapper;
import org.mq.infra.message.activeMq.publisher.model.ActiveMqPublisher;
import org.mq.infra.message.activeMq.publisher.model.PublisherJobProperties.PublisherJobProperty;
import org.mq.infra.message.activeMq.publisher.register.MultiThreadActiveMqPublisherRegister;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.test.context.ActiveProfiles;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
//@ActiveProfiles("test")
@SpringBootTest
class ActiveMqPublisherManagerTest {

    @Autowired
    private JmsTemplate jmsTemplate;

    @Autowired
    private SqlMapper mapper;

    @Autowired
    private DataSource dataSource;


    static List<PublisherJobProperty> properties = new ArrayList<>();

    @BeforeAll
    static void init() {

        PublisherJobProperty property1 = new PublisherJobProperty();
        property1.setTopicName("testTopic11");
        property1.setTableName("authlog");
        property1.setThreadSize(2);
        property1.setIntervalMillis(1000);

        PublisherJobProperty property2 = new PublisherJobProperty();
        property2.setTopicName("testTopic22");
        property2.setTableName("testtbl");
        property2.setThreadSize(2);
        property2.setIntervalMillis(1000);

        properties.add(property1);
//        properties.add(property2);

    }


    @Test
    void 단일_스레드_Publisher_생성_성공() throws InterruptedException, SQLException {

        ActiveMqPublisherRegister register  = new MultiThreadActiveMqPublisherRegister(jmsTemplate, mapper);

        ActiveMqPublisherManager publisherManager = new ActiveMqPublisherManager(register);
        System.out.println("jms :: " + jmsTemplate);
        System.out.println("jms :: " + jmsTemplate.getConnectionFactory());

        for (PublisherJobProperty property : properties) {
            ActiveMqPublisher publisher = publisherManager.createPublisher(property);

            log.info(publisher.toString());
            publisherManager.registerPublisher(publisher);
        }

        Thread.sleep(2000);

        log.info("{}", dataSource.getConnection());
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