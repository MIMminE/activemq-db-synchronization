package org.mq.infra.message.activeMq.publisher.register;

import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.mq.infra.message.activeMq.publisher.ActiveMqPublisherRegister;
import org.mq.infra.message.activeMq.publisher.mapper.SqlMapper;
import org.mq.infra.message.activeMq.publisher.model.ActiveMqPublisher;
import org.mq.infra.message.activeMq.publisher.model.PublisherJobProperties.PublisherJobProperty;
import org.springframework.jms.core.JmsTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@ToString
public class MultiThreadActiveMqPublisherRegister implements ActiveMqPublisherRegister {

    @Getter
    private final Map<ActiveMqPublisher, Runnable> runnableRegistry = new ConcurrentHashMap<>();

    private final JmsTemplate jmsTemplate;
    private final SqlMapper mapper;


    public MultiThreadActiveMqPublisherRegister(JmsTemplate jmsTemplate, SqlMapper mapper) {
        this.jmsTemplate = jmsTemplate;
        this.mapper = mapper;
    }

    @Override
    public ActiveMqPublisher createPublisher(PublisherJobProperty jobProperty) {
        ConcurrentHashMap<String, List<Integer>> timedDivision = timeDivision(jobProperty, new ConcurrentHashMap<>());
        ActiveMqPublisher publisher = new ActiveMqPublisher(jmsTemplate, mapper, jobProperty);

        for (List<Integer> modIndexList : timedDivision.values()) {
            runnableRegistry.put(publisher, () -> {
                for (Integer modIndex : modIndexList) {
                    log.info("modIndex list = {}",modIndexList);
                    log.info("{}, {}", jobProperty.getTableName(), modIndex);
                    List<Map<String, Object>> rows = mapper.selectTable(jobProperty.getTableName(), modIndex);
                    
                    for (Map<String, Object> row : rows) {
                        System.out.println(row);
                        log.info("ac : {}",jmsTemplate);
//                        row.put("time_stamp", row.get("time_stamp").toString());
                        //TODO : Time_stamp 필드, 테이블별로 지정하기
//                        jmsTemplate.convertAndSend(job.getTopicName(),"test");
                        jmsTemplate.convertAndSend(jobProperty.getTopicName(), "test");

                        log.info(row.toString());
                    }
                }
            });
        }
        return publisher;
    }


    @Override
    public void registerPublisher(ActiveMqPublisher publisher) {
        // 스케줄 스레드 풀 생성
        // TaskExecutorPool 생성
        Runnable runnable = runnableRegistry.get(publisher);
        log.info("runnable 실행 : {}",runnable);
        new Thread(runnable).start();

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
