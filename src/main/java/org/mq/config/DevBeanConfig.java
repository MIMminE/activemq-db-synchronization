//package org.mq.config;
//
//import org.mq.messaging.module.publisher.PublisherGenerator;
//import org.mq.register.publisher.JobProperties;
//import org.mq.register.publisher.PublisherRegister;
//import org.springframework.boot.task.ThreadPoolTaskExecutorBuilder;
//import org.springframework.boot.task.ThreadPoolTaskSchedulerBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Profile;
//import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;
//
//@Configuration
//@Profile("dev")
//public class DevBeanConfig {
//
//    @Bean
//    public PublisherRegister publisherRegister(
//            ThreadPoolTaskSchedulerBuilder schedulerBuilder,
//            ThreadPoolTaskExecutorBuilder executorBuilder,
//            JobProperties jobProperties,
//            PublisherGenerator publisherRunnableGenerator) {
//        return new PublisherRegister(schedulerBuilder, executorBuilder, jobProperties, publisherRunnableGenerator);
//    }
//
////    @Bean
////    public ConsumerRegister consumerRegister(
////            ConsumerGenerator consumerGenerator,
////            DefaultJmsListenerContainerFactory containerFactory,
////            JmsListenerEndpointRegistry registry,
////            JobProperties jobProperties
////    ) {
////        return new ConsumerRegister(consumerGenerator, containerFactory, registry, jobProperties);
////    }
//
////    @Bean
////    public ConsumerGenerator consumerGenerator(
////            DefaultConsumerListener defaultConsumerListener,
////            @Qualifier("handleMethodFactory") // integrationMessageHandlerMethodFactory 도 가능
////            DefaultMessageHandlerMethodFactory handlerMethodFactory) {
////        return new ConsumerGenerator(defaultConsumerListener, handlerMethodFactory);
////    }
//
//    @Bean(name = "handleMethodFactory")
//    public DefaultMessageHandlerMethodFactory handlerMethodFactory() {
//        DefaultMessageHandlerMethodFactory factory = new DefaultMessageHandlerMethodFactory();
//        factory.afterPropertiesSet();
//        return factory;
//    }
//}
