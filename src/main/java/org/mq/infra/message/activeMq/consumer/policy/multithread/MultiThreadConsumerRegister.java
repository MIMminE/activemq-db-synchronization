//package org.mq.infra.message.activeMq.consumer.policy.multithread;
//
//import org.mq.infra.message.activeMq.consumer.ActiveMqConsumerRegister;
//import org.mq.infra.message.activeMq.consumer.ActiveMqConsumer;
//import org.mq.infra.message.activeMq.consumer.method.ExternalServerDefaultMethod;
//import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
//import org.springframework.jms.config.JmsListenerEndpointRegistry;
//import org.springframework.jms.config.MethodJmsListenerEndpoint;
//import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;
//
//import java.lang.reflect.Method;
//import java.util.Map;
//
//public class MultiThreadConsumerRegister extends JmsListenerEndpointRegistry implements ActiveMqConsumerRegister<MultiThreadConsumerConfig> {
//
//    private final DefaultMessageHandlerMethodFactory methodFactory;
//    private final DefaultJmsListenerContainerFactory listenerContainerFactory;
//    private final ExternalServerDefaultMethod activeMqListener;
//
//    public MultiThreadConsumerRegister(
//            DefaultMessageHandlerMethodFactory methodFactory,
//            DefaultJmsListenerContainerFactory listenerContainerFactory,
//            ExternalServerDefaultMethod activeMqListener) {
//        this.methodFactory = methodFactory;
//        this.listenerContainerFactory = listenerContainerFactory;
//        this.activeMqListener = activeMqListener;
//    }
//
//    @Override
//    public ActiveMqConsumer createConsumer(MultiThreadConsumerConfig jobProperty) {
//
//        MethodJmsListenerEndpoint endpoint = new MethodJmsListenerEndpoint();
//        Method method = null;
//        try {
//            method = activeMqListener.getClass().getMethod("listenerExecute", Map.class);
//        } catch (NoSuchMethodException e) {
//            throw new RuntimeException(e); // TODO :: 예외 추상화
//        }
//
//        endpoint.setId(jobProperty.getTableName() + "listener");
//        endpoint.setBean(activeMqListener);
//        endpoint.setMethod(method);
//        endpoint.setDestination(jobProperty.getSyncTableProperties().getTopicName());
//        endpoint.setMessageHandlerMethodFactory(methodFactory);
//
//        return ActiveMqConsumer.builder()
//                .endpoint(endpoint)
//                .factory(listenerContainerFactory)
//                .startImmediately(true)
//                .build();
//    }
//
//    @Override
//    public void registerConsumer(ActiveMqConsumer consumer) {
//        super.registerListenerContainer(
//                consumer.getEndpoint(),
//                consumer.getFactory(),
//                consumer.isStartImmediately());
//    }
//}
