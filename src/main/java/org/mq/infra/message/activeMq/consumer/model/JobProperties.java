package org.mq.infra.message.activeMq.consumer.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Data
//@Component
//@ConfigurationProperties(prefix = "db-sync")
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
