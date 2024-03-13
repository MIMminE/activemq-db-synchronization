package org.mq.register.publisher;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Component
@ConfigurationProperties(prefix = "db-sync")
public class JobProperties {
    private List<JobProperty> jobs;

    @Getter
    @Setter
    @ToString
    public static class JobProperty {

        private String tableName;
        private String topicName;
        private int threadSize;
        private int intervalMillis;
    }
}
