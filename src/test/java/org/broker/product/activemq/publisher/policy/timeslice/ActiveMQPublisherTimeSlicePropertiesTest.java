package org.broker.product.activemq.publisher.policy.timeslice;

import org.assertj.core.api.Assertions;
import org.assertj.core.groups.Tuple;
import org.broker.product.activemq.publisher.policy.timeslice.ActiveMQPublisherTimeSliceProperties.SyncInfoProperties;
import org.broker.support.IntegrationPublisherTimeSliceSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

@DisplayName("[Integration] ActiveMQ Publisher TimeSlice Properties")
class ActiveMQPublisherTimeSlicePropertiesTest extends IntegrationPublisherTimeSliceSupport {

    @DisplayName("app.config.activemq.publisher.timeslice 설정이 존재할 경우 TimeSlicePolicy 설정을 정상적으로 불러온다.")
    @Test
    void readProperties() {
        // given
        List<SyncInfoProperties> syncInfo = properties.getSyncInfo();

        // when // then
        Assertions.assertThat(syncInfo).hasSize(2)
                .extracting("sourceTable", "topic", "threadSize", "intervalMillis")
                .contains(
                        Tuple.tuple("auth_log_tbl", "auth_topic", 7, 300),
                        Tuple.tuple("system_log_tbl", "system_topic", 3, 1000)
                );
    }

}