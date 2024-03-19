package org.mq.infra.message.activeMq.publisher.register;

import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.mq.exception.PublisherSendException;
import org.mq.infra.message.activeMq.publisher.ActiveMqPublisherRegister;
import org.mq.infra.message.activeMq.publisher.mapper.SqlMapper;
import org.mq.infra.message.activeMq.publisher.model.ActiveMqPublisher;
import org.mq.infra.message.activeMq.publisher.model.PublisherJobProperties.PublisherJobProperty;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.task.ThreadPoolTaskExecutorBuilder;
import org.springframework.boot.task.ThreadPoolTaskSchedulerBuilder;
import org.springframework.jms.JmsException;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@ToString
public class MultiThreadActiveMqPublisherRegister implements ActiveMqPublisherRegister {

    @Getter
    private final Map<ActiveMqPublisher, List<Runnable>> runnableRegistry = new ConcurrentHashMap<>();

    private final JmsTemplate jmsTemplate;
    private final SqlMapper mapper;


    public MultiThreadActiveMqPublisherRegister(@Qualifier("mqJmsPlate") JmsTemplate jmsTemplate, SqlMapper mapper) {
        this.jmsTemplate = jmsTemplate;
        this.mapper = mapper;
    }

    @Override
    public ActiveMqPublisher createPublisher(PublisherJobProperty jobProperty) {
        ConcurrentHashMap<String, List<Integer>> timedDivision = timeDivision(jobProperty, new ConcurrentHashMap<>());
        ActiveMqPublisher publisher = new ActiveMqPublisher(jmsTemplate, mapper, jobProperty);
        List<Runnable> runnableList = new ArrayList<>();

        for (List<Integer> modIndexList : timedDivision.values()) {
            runnableList.add(() -> {
                for (Integer modIndex : modIndexList) {
                    List<Map<String, Object>> rows = mapper.selectTable(jobProperty.getTableName(), modIndex);

                    for (Map<String, Object> row : rows) {
                        // time_stamp 타입은 지원하지 않음. TODO : Time_stamp 필드, 테이블별로 지정하기

                        row.put("TIME_STAMP", row.get("TIME_STAMP").toString());
                        log.info(row.toString());

                        try {
                            jmsTemplate.convertAndSend(jobProperty.getTopicName(), row);
                        } catch (JmsException e) {
                            throw new PublisherSendException(e.getMessage(), e.getCause());
                        }
                    }
                }
            });
            runnableRegistry.put(publisher, runnableList);
        }
        return publisher;
    }


    @Override
    public void registerPublisher(ActiveMqPublisher publisher) {
        List<Runnable> runnableList = runnableRegistry.get(publisher);
        ThreadPoolTaskExecutor executorPool = createExecutorPool(publisher);
        registerSchedule(executorPool, runnableList, publisher.getProperty().getIntervalMillis());
    }

    private ThreadPoolTaskScheduler registerSchedule(ThreadPoolTaskExecutor executorPool, List<Runnable> runnableList, int intervalMillis) {
        executorPool.initialize();
        return new ThreadPoolTaskSchedulerBuilder()
                .customizers(taskExecutor -> {
                    taskExecutor.initialize();
                    taskExecutor.scheduleAtFixedRate(() -> {
                        for (Runnable runnable : runnableList) {
                            executorPool.submit(runnable);
                        }
                    }, Duration.ofMillis(intervalMillis));
                })
                .build();
    }


    private ThreadPoolTaskExecutor createExecutorPool(ActiveMqPublisher publisher) {
        return new ThreadPoolTaskExecutorBuilder()
                .corePoolSize(publisher.getProperty().getThreadSize())
                .maxPoolSize(publisher.getProperty().getThreadSize())
                .threadNamePrefix(publisher.getProperty().getTableName() + " - ")
                .build();
    }


    private ConcurrentHashMap<String, List<Integer>> timeDivision(PublisherJobProperty jobProperty, ConcurrentHashMap<String, List<Integer>> timeDivisionMap) {
        for (int i = 0; i < jobProperty.getThreadSize(); i++) {
            timeDivisionMap.put(String.format("%s-%s", jobProperty.getTableName(), i), new ArrayList<>());
        }

        for (int i = 0; i <= 9; i++) {
            timeDivisionMap.get(String.format("%s-%s", jobProperty.getTableName(), i % jobProperty.getThreadSize())).add(i);
        }
        return timeDivisionMap;
    }

}
