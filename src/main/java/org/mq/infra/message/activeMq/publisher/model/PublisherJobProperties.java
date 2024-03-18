package org.mq.infra.message.activeMq.publisher.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Data
//@Component
//@ConfigurationProperties(prefix = "db-sync")
public class PublisherJobProperties {
    private List<PublisherJobProperty> jobs;

    @Getter
    @Setter
    @ToString
    public static class PublisherJobProperty {

        private String tableName;
        private String topicName;
        private int threadSize;
        private int intervalMillis;
    }
}
