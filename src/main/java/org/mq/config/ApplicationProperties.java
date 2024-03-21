package org.mq.config;

import lombok.Data;
import lombok.ToString;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@ToString
@Configuration
public class ApplicationProperties {

    @Configuration
    @ConfigurationProperties(
            prefix = "app.config.activemq"
    )
    @Data
    public class AppConfigProperties {
        private String mode = "default";
        private String user;
        private String password;
        private String brokerUrl;

    }

    @Configuration
    @ConditionalOnProperty(name = "app.config.activemq.mode", havingValue = "default")
    @ConfigurationProperties(
            prefix = "app.db-sync"
    )
    @Data
    public class SyncTableProperties {
        private List<Job> jobs;

        @Data
        public static class Job {
            String tableName;
            String topicName;
            int threadSize;
            int intervalMillis;
        }
    }
}
