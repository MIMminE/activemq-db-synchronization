package org.mq;

import lombok.RequiredArgsConstructor;
import org.mq.jpa.AuthlogEntity;
import org.mq.jpa.repo;
import org.springframework.data.domain.AfterDomainEventPublication;
import org.springframework.data.domain.DomainEvents;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Component
@RequiredArgsConstructor
public class EventExec {
    private final repo repo;
    private final JmsTemplate jmsTemplate;

//    @Transactional
//    @Scheduled(fixedRate = 3000)
//    public void addData() {
//        System.out.println("삽입 발생");
//        AuthlogEntity entity = new AuthlogEntity();
//        entity.setHostname("testHost");
//        entity.setUserName("testUser");
//        entity.setTimestamp(new Date());
//        entity.setResult(1);
//        entity.setReason("Test Reason");
//        entity.setCliMac("testMAC");
//        entity.setNasId("testNas");
//        entity.setVersion("testVersion");
//        entity.setCliIp(123456);
//        entity.setFailCode("testCode");
//
//        // 데이터베이스에 삽입
//        repo.save(entity);
//    }

//    @DomainEvents
//    Iterable<AuthlogEntity> domainEvents() {
//        System.out.println("이벤트가 감지되었습니다.");
//        return repo.findAll();
//    }
//
//    @JmsListener(destination = "dest")
//    public void receive(String message) throws InterruptedException {
//        Thread.sleep(100);
//        System.out.println(Thread.currentThread().getName() + " : 메시지 전달받음 " + message);
//    }
//
//    @AfterDomainEventPublication
//    void afterPublication() {
//        // 추가된 로우에 대한 것만 jmsTemplate로 전송
//        for (AuthlogEntity entity : domainEvents()) {
//            System.out.println("이벤트를 전송합니다.");
//            jmsTemplate.convertAndSend("mydest", entity);
//        }
//    }
}
