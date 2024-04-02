package org.broker.product.activemq.consumer.policy.basic;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "app.config.activemq.consumer.basic")
@Data
public class ActiveMQConsumerBasicProperties {
    private List<SyncInfoProperties> syncInfo;

    @Data
    @AllArgsConstructor
    public static class SyncInfoProperties {
        private String destinationTable;
        private String topic;
    }
}