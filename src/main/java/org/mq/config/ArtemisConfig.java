//package org.mq.config;
//
//import jakarta.jms.JMSException;
//import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.jms.connection.CachingConnectionFactory;
//import org.springframework.jms.core.JmsTemplate;
//import org.springframework.jndi.JndiObjectFactoryBean;
//
//import javax.management.MBeanServerConnection;
//import javax.management.MBeanServerInvocationHandler;
//import javax.management.ObjectName;
//import java.lang.management.ManagementFactory;
//
//@Configuration
//public class ArtemisConfig implements CommandLineRunner {
//
//    @Bean
//    public ActiveMQConnectionFactory connectionFactory() throws JMSException {
//        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory();
//        factory.setUser("artemis");
//        factory.setPassword("artemis");
//        factory.setBrokerURL("tcp://localhost:61616");
//        return factory;
//    }
//
//    @Bean
//    public JmsTemplate jmsTemplate(ActiveMQConnectionFactory connectionFactory) {
//        JmsTemplate jmsTemplate = new JmsTemplate();
//        jmsTemplate.setConnectionFactory(connectionFactory);
//        return jmsTemplate;
//    }
//
//    @Override
//    public void run(String... args) throws Exception {
//        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
//        connectionFactory.setTargetConnectionFactory(connectionFactory());
//
//        JndiObjectFactoryBean jndiObjectFactoryBean = new JndiObjectFactoryBean();
//        jndiObjectFactoryBean.setJndiName("connectionFactory");
//        jndiObjectFactoryBean.setExpectedType(CachingConnectionFactory.class);
//        jndiObjectFactoryBean.afterPropertiesSet();
//
//        MBeanServerConnection mbeanServer = ManagementFactory.getPlatformMBeanServer();
//        ObjectName objectName = new ObjectName("org.apache.activemq:brokerName=localhost,type=ConnectionFactory");
//        Object proxy = MBeanServerInvocationHandler.newProxyInstance(mbeanServer, objectName, CachingConnectionFactory.class, true);
//
//        CachingConnectionFactory proxyConnectionFactory = (CachingConnectionFactory) proxy;
//        proxyConnectionFactory.crea("my-dynamic-topic");
//
//        // 4. 닫기
//    }
//}
