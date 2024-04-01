package org.broker.support;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;
import org.broker.mapper.QueryMapper;
import org.broker.product.activemq.ActiveMQProperties;
import org.broker.product.activemq.ActiveMQServer;
import org.broker.product.activemq.publisher.policy.timeslice.ActiveMQPublisherTimeSliceProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles(profiles = "publisher-time-slice-test")
@SpringBootTest
@Import(IntegrationPublisherTimeSliceSupport.config.class)
public class IntegrationPublisherTimeSliceSupport {

    @Autowired
    protected ActiveMQPublisherTimeSliceProperties properties;

    @Autowired
    protected JmsTemplate jmsTemplate;

    @Autowired
    protected QueryMapper mapper;

    static class config{
        @Bean
        public JmsTemplate jmsTemplate(ActiveMQProperties properties){
            ActiveMQServer activeMQServer = new ActiveMQServer(properties);
            ActiveMQConnectionFactory connectionFactory = activeMQServer.getConnectionFactory();
            return new JmsTemplate(connectionFactory);
        }
    }
}


