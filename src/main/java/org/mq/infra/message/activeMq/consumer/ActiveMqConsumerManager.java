//package org.mq.infra.message.activeMq.consumer;
//
//import lombok.extern.slf4j.Slf4j;
//import org.mq.domain.consumer.ConsumerManager;
//
//@Slf4j
//public class ActiveMqConsumerManager implements ConsumerManager<ActiveMqConsumer> {
//
//    private final ActiveMqConsumerRegister consumerRegister;
//
//    public ActiveMqConsumerManager(ActiveMqConsumerRegister consumerRegister) {
//        this.consumerRegister = consumerRegister;
//    }
//
//    @Deprecated
//    @Override
//    public ActiveMqConsumer createConsumer() {
//        return null;
//    }
//
//    @Override
//    public ActiveMqConsumer createConsumer(Object... params) {
//        return consumerRegister.createConsumer((ConsumerJobProperty) params[0]);
//    }
//
//    @Override
//    public void registerConsumer(ActiveMqConsumer consumer) {
//        consumerRegister.registerConsumer(consumer);
//    }
//}
