//package org.broker.product.activemq;
//
//import org.broker.model.PublisherPolicy;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.params.ParameterizedTest;
//import org.junit.jupiter.params.provider.MethodSource;
//
//import java.util.stream.Stream;
//
//@DisplayName("[Unit] ActiveMQPublisherFactory")
//class ActiveMQPublisherFactoryTest {
//
//    private static Stream<PublisherPolicy> providerPublisherPolicy(){
//
//        return Stream.of(
//        );
//    }
//
//    @DisplayName("Publisher Policy 의존성이 올바르게 주입된다.")
//    @MethodSource("providerPublisherPolicy")
//    @ParameterizedTest
//    void createPublisherFactory(PublisherPolicy policy) {
//        // given
//
//        // when
//
//        // then
//    }
//
//    @DisplayName("createServerInstance 메서드로 생성된 각 Policy 별 ActiveMQServer 객체를 올바르게 반환한다.")
//    @MethodSource("providerPublisherPolicy")
//    @ParameterizedTest
//    void createServerInstance() {
//        // given
//
//        // when
//
//        // then
//    }
//
//    @DisplayName("createPublishers 메서드로 각 Policy 별 Publisher 생성 로직을 호출한다.")
//    @MethodSource("providerPublisherPolicy")
//    @ParameterizedTest
//    void createPublishers() {
//        // given
//
//        // when
//
//        // then
//    }
//
//    @DisplayName("registerPublishers 메서드로 각 Policy 별 Publisher 등록 로직을 호출한다.")
//    @MethodSource("providerPublisherPolicy")
//    @ParameterizedTest
//    void registerPublishers() {
//        // given
//
//        // when
//
//        // then
//    }
//}