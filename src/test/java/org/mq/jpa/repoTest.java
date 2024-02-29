package org.mq.jpa;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

@SpringBootTest
class repoTest {

    @Autowired
    private repo repository;

    @Test
    public void testInsert() {
        // 테스트 데이터 생성
        AuthlogEntity entity = new AuthlogEntity();
        entity.setHostname("testHost");
        entity.setUserName("testUser");
        entity.setTimestamp(new Date());
        entity.setResult(1);
        entity.setReason("Test Reason");
        entity.setCliMac("testMAC");
        entity.setNasId("testNas");
        entity.setVersion("testVersion");
        entity.setCliIp(123456);
        entity.setFailCode("testCode");

        // 데이터베이스에 삽입
        repository.save(entity);

    }

}