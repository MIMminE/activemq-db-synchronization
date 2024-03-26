package org.broker;

import org.assertj.core.api.Assertions;
import org.broker.model.BrokerServer;
import org.broker.model.Consumer;
import org.broker.product.ConsumerFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collection;
import java.util.List;

import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("ConsumerManger 단위 테스트")
public class ConsumerManagerTest {

    @Mock
    ConsumerFactory<BrokerServer<?>, Consumer> mockConsumerFactory;

    @Mock
    BrokerServer brokerServer;


    @DisplayName("ConsumerManager 시나리오 테스트")
    @TestFactory
    Collection<DynamicTest> consumerManagerDynamicTests() {
        // given
        given(mockConsumerFactory.createServerInstance()).willReturn(brokerServer);
        given(mockConsumerFactory.createConsumers()).willReturn(
                List.of(mock(Consumer.class), mock(Consumer.class))
        );
        ConsumerManager<BrokerServer<?>, Consumer> consumerManger = new ConsumerManager<>(mockConsumerFactory);

        return List.of(
                DynamicTest.dynamicTest("createServerInstance 메소드 호출 시 " +
                        "ConsumerFactory 인스턴스의 Server 생성 메서드가 호출되고 BrokerServer 객체가 반환된다.", () -> {

                    // when
                    BrokerServer<?> serverInstance = consumerManger.createServerInstance();

                    // then
                    Assertions.assertThat(serverInstance).isInstanceOf(BrokerServer.class);
                    verify(mockConsumerFactory).createServerInstance();
                }),

                DynamicTest.dynamicTest("createConsumer 메소드 호출 시 " +
                        "ConsumerFactory 인스턴스의 Consumer 생성 메서드가 호출되고 Consumer 리스트가 반환된다.", () -> {

                    // when
                    List<Consumer> consumers = consumerManger.createConsumer();

                    // then
                    Assertions.assertThat(consumers)
                            .hasSize(2)
                            .allMatch(consumer -> consumer instanceof Consumer);
                    verify(mockConsumerFactory).createConsumers();
                }),

                DynamicTest.dynamicTest("registerConsumer 메소드 호출 시 " +
                        "ConsumerFactory 인스턴스의 Consumer 등록 메서드가가 호출된다.", () -> {

                    // given
                    List<Consumer> consumers = consumerManger.createConsumer();


                    // when
                    consumerManger.registerConsumer(consumers);

                    // then
                    verify(mockConsumerFactory).registerConsumer(consumers);
                })
        );
    }
}
