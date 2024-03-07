package org.mq.scheduler;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

@Component
@AllArgsConstructor
@Slf4j
public class TestSchedul implements CommandLineRunner {
    private ThreadPoolTaskScheduler threadPoolTaskScheduler;
    private Environment env;

    @Override
    public void run(String... args) throws Exception {
        threadPoolTaskScheduler.setPoolSize(10);
        for (int i = 0; i < 2; i++) {
            String property = env.getProperty("db-sync.jobs["+i+"].table-name");

            threadPoolTaskScheduler.scheduleAtFixedRate(() -> {
                log.info(Thread.currentThread().getName()+ " : "+ property);
                testThread testThread = new testThread();

                for (int j = 0; j < 10; j++){
                    try {
                        testThread.start();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }, Duration.ofMillis(3000));
        }
    }


    @Slf4j
    static class testThread {

        @Async
        public void start() throws InterruptedException {
            log.info(Thread.currentThread().getName()+ " : "+ "쓰레드 동작중!");
        }
    }
}
