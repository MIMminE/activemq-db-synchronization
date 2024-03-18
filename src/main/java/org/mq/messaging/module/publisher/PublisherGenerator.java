//package org.mq.messaging.module.publisher;
//
//import lombok.AllArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.mq.mapper.TableMapper;
//import org.mq.register.publisher.JobProperties;
//import org.springframework.jms.core.JmsTemplate;
//import org.springframework.stereotype.Component;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.concurrent.ConcurrentHashMap;
//
//@Slf4j
//@Component
//@AllArgsConstructor
//public class PublisherGenerator {
//    private TableMapper mapper;
//    private JmsTemplate jmsTemplate;
//
//    public List<Runnable> generatePublisherRunnable(JobProperties.JobProperty job) {
//        Map<String, List<Integer>> timeDivisionMap = new ConcurrentHashMap<>();
//        timeDivision(job, timeDivisionMap);
//        List<Runnable> runnableList = new ArrayList<>();
//
//        for (String key : timeDivisionMap.keySet()) {
//            runnableList.add(() -> {
//                List<Integer> modIndexList = timeDivisionMap.get(key);
//                for (Integer modIndex : modIndexList) {
//                    List<Map<String, Object>> rows = mapper.selectTable(job.getTableName(), modIndex);
//                    for (Map<String, Object> row : rows) {
//                        row.put("time_stamp", row.get("time_stamp").toString());
//                        //TODO : Time_stamp 필드, 테이블별로 지정하기
////                        jmsTemplate.convertAndSend(job.getTopicName(),"test");
//                        jmsTemplate.convertAndSend(job.getTopicName(),row);
//                        log.info(row.toString());
//                    }
//                }
//            });
//        }
//
//        return runnableList;
//    }
//
//    private void timeDivision(JobProperties.JobProperty job, Map<String, List<Integer>> timeDivisionMap) {
//        for (int i = 0; i < job.getThreadSize(); i++) {
//            timeDivisionMap.put(String.format("%s-%s", job.getTableName(), i), new ArrayList<>());
//        }
//
//        for (int i = 0; i <= 9; i++) {
//            timeDivisionMap.get(String.format("%s-%s", job.getTableName(), i % job.getThreadSize())).add(i);
//        }
//    }
//}
//
