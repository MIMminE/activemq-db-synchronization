package org.broker.mapper;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

@ActiveProfiles("h2-db-test")
@SpringBootTest
@Transactional
@DisplayName("[Integration] QueryMapper")
class QueryMapperTest {

    @Autowired
    private QueryMapper queryMapper;

    @DisplayName("selectById 메서드로 각 테이블의 고유 id의 데이터를 가져오는 것에 성공한다.")
    @Test
    void selectById() {

        // when
        Map<String, Object> result = queryMapper.selectById("auth_log_tbl", 1L);

        // then
        Assertions.assertThat(result)
                .extracting("CLI_IP", "ID_FLAG", "SYNC_FLAG")
                .contains("test-ip-1", 1L, false);
    }


    private static Stream<Arguments> provideParam() {
        return Stream.of(
                Arguments.of(0, 0),
                Arguments.of(1, 2),
                Arguments.of(2, 1),
                Arguments.of(3, 1),
                Arguments.of(4, 1),
                Arguments.of(5, 1),
                Arguments.of(6, 1),
                Arguments.of(7, 0),
                Arguments.of(8, 0),
                Arguments.of(9, 0)
        );
    }

    @DisplayName("modIndex 값에 따라 대상 테이블에서 올바른 결과를 Select한다.")
    @MethodSource("provideParam")
    @ParameterizedTest
    void selectTableBySyncCondition(int modIndex, int expected) {
        // when
        List<Map<String, Object>> result = queryMapper.selectTableBySyncCondition("auth_log_tbl", modIndex);
        System.out.println(result);
        // then
        Assertions.assertThat(result).hasSize(expected);
        result.stream()
                .map(value -> (Timestamp) value.get("TIME_STAMP"))
                .map(Timestamp::toLocalDateTime)
                .map(LocalDateTime::getSecond)
                .forEach(second -> {
                    Assertions.assertThat(second).isEqualTo(modIndex);
                });

    }


    @DisplayName("updateTableSyncCondition 메서드로 sync_flag 값을 업데이트한다.")
    @Test
    void updateTableSyncCondition() {

        // given
        Map<String, Object> beforeUpdate = queryMapper.selectById("auth_log_tbl", 1L);


        // when
        queryMapper.updateTableSyncCondition("auth_log_tbl", 1L);
        Map<String, Object> afterUpdate = queryMapper.selectById("auth_log_tbl", 1L);

        // then
        Assertions.assertThat(afterUpdate.get("SYNC_FLAG"))
                .isNotEqualTo(beforeUpdate.get("SYNC_FLAG"));

        Assertions.assertThat(afterUpdate)
                .extracting("ID_FLAG", "SYNC_FLAG")
                .contains(1L, true);
    }

    @DisplayName("insertTopicMessage 테스트")
    @Test
    void insertTopicMessage() {
        // given
        Map<String, Object> maps = new HashMap<>();
        maps.put("name", "test");
        maps.put("time_stamp", Timestamp.valueOf("2024-01-12 12:12:12"));
        maps.put("age", 15);

        queryMapper.insertTopicMessage("system_log_tbl", maps);
        // when
        Map<String, Object> stringObjectMap = queryMapper.selectById("system_log_tbl", 6L);
        System.out.println(stringObjectMap);
        // then
    }
}