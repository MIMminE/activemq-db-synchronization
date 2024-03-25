//package org.mq.config;
//
//import org.assertj.core.api.Assertions;
//import org.assertj.core.groups.Tuple;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.mq.config.ApplicationProperties.AppConfigProperties;
//import org.mq.config.ApplicationProperties.SyncTableProperties;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.ActiveProfiles;
//
//
//@ActiveProfiles("property-test")
//@SpringBootTest
//class ApplicationPropertiesHappyCaseTest {
//
//    @Autowired
//    private AppConfigProperties appConfigProperties;
//
//    @Autowired
//    private SyncTableProperties syncTableProperties;
//
//    @DisplayName("프로그램 설정 'app.config.activemq' 로드를 정상적으로 수행한다.")
//    @Test
//    void setupProperties() {
//        Assertions.assertThat(appConfigProperties)
//                .extracting("mode", "user", "password", "brokerUrl")
//                .contains("default", "test_user", "test_password", "tcp://localhost:61616");
//    }
//
//    @DisplayName("'app.config.activemq.mode'가 'default'이면 테이블 싱크 정보 설정이 로드된다.")
//    @Test
//    void test() {
//        Assertions.assertThat(syncTableProperties.getJobs())
//                .extracting("tableName", "topicName", "threadSize", "intervalMillis")
//                .isNotNull()
//                .containsExactlyInAnyOrder(
//                        Tuple.tuple("test1_table", "test1_topic", 10, 10000),
//                        Tuple.tuple("test2_table", "test2_topic", 5, 2000)
//                );
//    }
//}