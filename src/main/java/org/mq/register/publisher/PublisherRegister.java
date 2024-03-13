package org.mq.register.publisher;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mq.messaging.module.publisher.PublisherGenerator;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.task.ThreadPoolTaskExecutorBuilder;
import org.springframework.boot.task.ThreadPoolTaskSchedulerBuilder;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.time.Duration;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class PublisherRegister implements CommandLineRunner {
    private final ThreadPoolTaskSchedulerBuilder schedulerBuilder;
    private final ThreadPoolTaskExecutorBuilder executorBuilder;
    private final JobProperties jobProperties;
    private final PublisherGenerator publisherRunnableGenerator;

    private void init() {
        for (JobProperties.JobProperty job : jobProperties.getJobs()) {
            ThreadPoolTaskExecutor executor = buildJobExecutor(job);
            List<Runnable> runnableList = publisherRunnableGenerator.generatePublisherRunnable(job);
            BuildingScheduler(job.getIntervalMillis(), executor, runnableList);
        }
    }

    private void BuildingScheduler(int intervalMillis, ThreadPoolTaskExecutor executor, List<Runnable> runnableList) {
        schedulerBuilder
                .customizers(taskScheduler -> {
                    taskScheduler.initialize();
                    taskScheduler.scheduleAtFixedRate(() -> {
                        for (Runnable runnable : runnableList) {
                            executor.execute(runnable);
                        }
                    }, Duration.ofMillis(intervalMillis));
                }).build();
    }

    private ThreadPoolTaskExecutor buildJobExecutor(JobProperties.JobProperty job) {
        ThreadPoolTaskExecutor executor = executorBuilder
                .corePoolSize(job.getThreadSize())
                .maxPoolSize(job.getThreadSize())
                .threadNamePrefix(job.getTableName() + "-")
                .build();

        executor.initialize();
        return executor;
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("Running CommandLineRunner Class : {}", this.getClass());
        init();
    }
}
