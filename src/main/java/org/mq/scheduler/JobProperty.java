package org.mq.scheduler;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class JobProperty {

    private String tableName;
    private String topicName;
    private int threadSize;
    private int intervalMillis;
}
