package org.broker.product.activemq.consumer.policy.basic;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "app.config.activemq.consumer.basic")
@Data
public class ActiveMQConsumerBasicProperties {
    private List<SyncInfoProperties> syncInfo;

    @Data
    public static class SyncInfoProperties {
        String destinationTable;
        String topic;
    }
}