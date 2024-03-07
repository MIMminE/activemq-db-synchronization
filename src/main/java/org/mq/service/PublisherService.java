package org.mq.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mq.thread.PublisherThread;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Profile("!test")
@RequiredArgsConstructor
@Service
@Slf4j
public class PublisherService {

    private final PublisherThread thread;

    @Value("${spring.task.execution.pool.core-size}")
    private int taskSize;

    @Value("${spring.task.execution.thread-name-prefix}")
    private String taskName;

    Map<String, List<Integer>> assignThread = new HashMap<>();

    @Scheduled(fixedRateString = "${env.publisher.interval-millis}")
    public void run() throws InterruptedException {
        assignThreadInit();

        for (String key : assignThread.keySet()) {
            thread.publish(assignThread.get(key));
        }
    }

    private void assignThreadInit() {
        for (int i = 0; i < taskSize; i++) {
            assignThread.put(String.format("%s%s", taskName, i), new ArrayList<>());
        }

        for (int i = 0; i <= 9; i++) {
            assignThread.get(String.format("%s%s", taskName, i % taskSize)).add(i);
        }
    }
}
