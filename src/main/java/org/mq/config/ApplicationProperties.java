package org.mq.config;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@ConfigurationProperties(
        prefix = "app.config.activemq"
)
@NoArgsConstructor
@Data
public class ApplicationProperties {
    private String mode = "default";
    private String user;
    private String password;
    private String brokerUrl;


    @ConditionalOnProperty(name = "app.config.activemq.mode", havingValue = "default")
    @ConfigurationProperties(
            prefix = "app.db-sync"
    )
    @Data
    static class SyncProperties {
        private List<Job> job;

        static class Job {
            String tableName;
            String topicName;
            int threadSize;
            int intervalMillis;
        }

    }
}
