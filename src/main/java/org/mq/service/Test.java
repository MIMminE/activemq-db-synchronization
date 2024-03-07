//package org.mq.service;
//
//import lombok.RequiredArgsConstructor;
//import org.mq.mapper.TableMapper;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.core.env.Environment;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.Map;
//
//@Service
//@RequiredArgsConstructor
//public class Test implements CommandLineRunner {
//    private final TableMapper tableMapper;
//    private final Environment env;
//
//    @Override
//    public void run(String... args) throws Exception {
//        String tableName = env.getProperty("db-sync.jobs[0].table-name");
//
//        List<Map<String, Object>> logDTOList = tableMapper.selectTable(tableName);
//        for (Map<String, Object> stringObjectMap : logDTOList) {
//            stringObjectMap.put("table_name", tableName);
//            stringObjectMap.put("sync_flag", true);
//            tableMapper.updateTable(stringObjectMap);
//        }
//    }
//}
