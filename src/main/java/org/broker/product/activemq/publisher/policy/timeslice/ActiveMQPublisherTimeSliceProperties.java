package org.broker.product.activemq.publisher.policy.timeslice;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "app.config.activemq.publisher.timeslice")
@Data
public class ActiveMQPublisherTimeSliceProperties {
    private List<SyncInfoProperties> syncInfo;

    @Data
    @AllArgsConstructor
    public static class SyncInfoProperties {
        private String sourceTable;
        private String topic;
        private int threadSize;
        private int intervalMillis;
    }
}