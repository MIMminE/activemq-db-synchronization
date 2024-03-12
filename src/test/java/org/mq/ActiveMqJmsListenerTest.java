package org.mq;

import jakarta.jms.ConnectionFactory;
import jakarta.jms.Message;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.mq.message.DynamicJmsListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jms.config.*;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.listener.DefaultMessageListenerContainer;
import org.springframework.jms.listener.MessageListenerContainer;
import org.springframework.jms.listener.SimpleMessageListenerContainer;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;
import org.springframework.messaging.handler.annotation.support.MessageHandlerMethodFactory;
import org.springframework.test.context.ActiveProfiles;

import java.lang.reflect.Method;
import java.util.Collection;

@ActiveProfiles("test")
@SpringBootTest
public class ActiveMqJmsListenerTest {

    @Autowired
    JmsTemplate jmsTemplate;

    @Qualifier("jmsConnectionFactory")
    @Autowired
    private ConnectionFactory factory;

    @Autowired
    private DefaultJmsListenerContainerFactory factory1;

    @Autowired
    private JmsListenerEndpointRegistry registry;


    static class testEnd{
        public void endMethod(){
            System.out.println("hi");
        }
    }
    @Test
    void basic() throws InterruptedException, NoSuchMethodException {
        System.out.println(factory1);
        System.out.println("jmsTemplate = " + jmsTemplate.getClass());

        MethodJmsListenerEndpoint endpoint = new MethodJmsListenerEndpoint();
        Method method = testEnd.class.getMethod("endMethod");
        Object bean = new testEnd();

        endpoint.setBean(bean);
        endpoint.setDestination("testendpoint");
        endpoint.setId("testendpoint");
        endpoint.setMethod(method);
        endpoint.setMessageHandlerMethodFactory(new DefaultMessageHandlerMethodFactory());
        registry.registerListenerContainer(endpoint, factory1, true);


        Collection<MessageListenerContainer> listenerContainers = registry.getListenerContainers();
        for (MessageListenerContainer listenerContainer : listenerContainers) {
            System.out.println("메시지 리스너 : "+ listenerContainer);
        }


        while(true){
            Thread.sleep(2000);
            jmsTemplate.convertAndSend("test","test");
            System.out.println("메시지 전송");
        }

//
//
//        final String destination = "test";
//        final String messageText = "hello";
//        jmsTemplate.setDefaultDestinationName(destination);
//        new Thread(()->{
//            while(true){
//                try {
//                    Thread.sleep(1000);
//                } catch (InterruptedException e) {
//                    throw new RuntimeException(e);
//                }
//                jmsTemplate.convertAndSend(messageText);
//
//
//            }
//        }).start();
//        ConnectionFactory connectionFactory = jmsTemplate.getConnectionFactory();
//        System.out.println(connectionFactory);
//        container.init();
//        SimpleMessageListenerContainer container1 = container.getContainer();
//        System.out.println(container1.isRunning());
//        while(true){
//            Message message = jmsTemplate.receive(destination);
//            System.out.println("message = " + message);
//        }
    }
}
