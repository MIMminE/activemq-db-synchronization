package org.mq.infra.message.activeMq.consumer.policy.multithread;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.mq.config.ApplicationProperties;
import org.mq.config.ApplicationProperties.AppConfigProperties;
import org.mq.config.ApplicationProperties.SyncTableProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@RequiredArgsConstructor
@ConditionalOnBean(SyncTableProperties.class)
@Getter
public class MultiThreadConsumerConfig {

    private final AppConfigProperties appConfigProperties;
    private final SyncTableProperties syncTableProperties;

    public List<SyncTableProperties.Job> getJobs(){
        return syncTableProperties.getJobs();
    }
}
