package org.mq.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mq.message.PublisherRunnableGenerator;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.task.ThreadPoolTaskExecutorBuilder;
import org.springframework.boot.task.ThreadPoolTaskSchedulerBuilder;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class SchedulerRegister implements CommandLineRunner {
    private final ThreadPoolTaskSchedulerBuilder schedulerBuilder;
    private final ThreadPoolTaskExecutorBuilder executorBuilder;
    private final JobProperties jobProperties;
    private final PublisherRunnableGenerator publisherRunnableGenerator;

    private void init() {
        for (JobProperty job : jobProperties.getJobs()) {
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

    private ThreadPoolTaskExecutor buildJobExecutor(JobProperty job) {
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
        init();
    }
}
