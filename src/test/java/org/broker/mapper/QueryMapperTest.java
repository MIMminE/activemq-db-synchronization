package org.broker.mapper;

import org.assertj.core.api.Assertions;
import org.jgroups.util.Tuple;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

@ActiveProfiles("h2-db-test")
@SpringBootTest
@Transactional
class QueryMapperTest {

    @Autowired
    private QueryMapper queryMapper;

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
        List<Map<String, Object>> result = queryMapper.selectTableBySyncCondition("authlog", modIndex);

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
}