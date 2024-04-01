package org.broker;


import org.assertj.core.api.Assertions;
import org.broker.model.BrokerServer;
import org.broker.model.Consumer;
import org.broker.model.Publisher;
import org.broker.product.PublisherFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collection;
import java.util.List;

import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("[Unit] PublisherManger")
public class PublisherManagerTest {

    @Mock
    PublisherFactory<BrokerServer<?>, Publisher> mockPublisherFactory;

    @Mock
    BrokerServer brokerServer;


    // TODO Dynamic Test
    @TestFactory
    Collection<DynamicTest> publisherManagerDynamicTests(){
        given(mockPublisherFactory.createServerInstance()).willReturn(brokerServer);
        given(mockPublisherFactory.createPublishers()).willReturn(
                List.of(mock(Publisher.class), mock(Publisher.class))
        );
        PublisherManager<BrokerServer<?>, Publisher> publisherManager = new PublisherManager<>(mockPublisherFactory);

       return List.of(
                DynamicTest.dynamicTest("createServerInstance 메소드 호출 시 " +
                        "PublisherFactory 인스턴스의 Server 생성 메서드가 호출되고 BrokerServer 객체가 반환된다.", () -> {

                    // when
                    BrokerServer<?> serverInstance = publisherManager.createServerInstance();

                    // then
                    Assertions.assertThat(serverInstance).isInstanceOf(BrokerServer.class);
                    verify(mockPublisherFactory).createServerInstance();
                }),

                DynamicTest.dynamicTest("createPublisher 메소드 호출 시 " +
                        "PublisherFactory 인스턴스의 Publisher 생성 메서드가 호출되고 Publisher 리스트가 반환된다.", () -> {

                    // when
                    List<Publisher> publishers = publisherManager.createPublisher();

                    // then
                    Assertions.assertThat(publishers)
                            .hasSize(2)
                            .allMatch(publisher -> publisher instanceof Publisher);
                    verify(mockPublisherFactory).createPublishers();
                }),

                DynamicTest.dynamicTest("registerPublisher 메소드 호출 시 " +
                        "PublisherFactory 인스턴스의 Publisher 등록 메서드가 호출된다.", () -> {

                    // given
                    List<Publisher> publisher =publisherManager.createPublisher();

                    // when
                    publisherManager.registerPublisher(publisher);

                    // then
                    verify(mockPublisherFactory).registerPublishers(publisher);
                })
        );
    }
}
