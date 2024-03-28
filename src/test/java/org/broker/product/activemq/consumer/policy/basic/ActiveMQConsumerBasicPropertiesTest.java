package org.broker.product.activemq.consumer.policy.basic;

import org.assertj.core.api.Assertions;
import org.assertj.core.groups.Tuple;
import org.broker.support.IntegrationConsumerBasicSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.broker.product.activemq.consumer.policy.basic.ActiveMQConsumerBasicProperties.*;

@DisplayName("[Integration] ActiveMQ Consumer Basic Properties")
public class ActiveMQConsumerBasicPropertiesTest extends IntegrationConsumerBasicSupport {

    @DisplayName("app.config.activemq.consumer.basic 설정이 있을 경우 Basic 설정을 정상적으로 불러온다.")
    @Test
    void readProperties() {
        // given
        List<SyncInfoProperties> syncInfo = properties.getSyncInfo();

        // when // then
        Assertions.assertThat(syncInfo).hasSize(2)
                .extracting("destinationTable", "topic")
                .contains(Tuple.tuple("auth_log_tbl", "auth_topic"),
                        Tuple.tuple("system_log_tbl", "system_topic"));

        Assertions.assertThat(properties).isInstanceOf(ActiveMQConsumerBasicProperties.class);
    }
}
