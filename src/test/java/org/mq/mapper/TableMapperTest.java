package org.mq.mapper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mq.infra.message.activeMq.publisher.mapper.SqlMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@SpringBootTest
class TableMapperTest {

    @Autowired
    private SqlMapper mapper;

    @DisplayName("[테이블명] 과 [time_stamp] 필드의 modIndex 입력 조건에 따른 MyBatis Select Query 가 올바르게 동작한다.")
    @Test
    void selectTableBySyncCondition() {

        // given : resource -> data.sql
        // when
        List<Map<String, Object>> results = mapper.selectTableBySyncCondition("authlog", 1);
        Map<String, Object> result0 = results.get(0);
        Map<String, Object> result1 = results.get(1);
        //then
        assertThat(results).hasSize(2);
        assertThat(result0)
                .containsEntry("CLI_IP", "test-ip-1")
                .containsEntry("SYNC_FLAG", false)
                .containsEntry("TIME_STAMP", Timestamp.valueOf("2024-03-04 10:13:01.0"));
        assertThat(result1)
                .containsEntry("CLI_IP", "test-ip-7")
                .containsEntry("SYNC_FLAG", false)
                .containsEntry("TIME_STAMP", Timestamp.valueOf("2024-03-04 10:19:01.0"));

    }
}