package org.mq.mapper;

import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class TableMapperTest {

    @Autowired
    private TableMapper mapper;

    @Test
    void selectTest() {
        List<Map<String, Object>> authlog = mapper.selectTable("authlog", 1);
        for (Map<String, Object> stringObjectMap : authlog) {
            System.out.println("stringObjectMap = " + stringObjectMap);
        }

        List<Map<String, Object>> authlog2 = mapper.selectTable("authlog", 2);
        for (Map<String, Object> stringObjectMap : authlog2) {
            System.out.println("stringObjectMap = " + stringObjectMap);
        }

    }


}