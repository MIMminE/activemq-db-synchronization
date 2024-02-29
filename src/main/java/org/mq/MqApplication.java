package org.mq;

import lombok.AllArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@AllArgsConstructor
@EnableScheduling
public class MqApplication {

    public static void main(String[] args) {
        SpringApplication.run(MqApplication.class, args);

    }
}

//    private JmsTemplate jmsTemplate;
//    private Listener listener;
//
//    @Override
//    public void run(String... args) throws Exception {
//        AtomicInteger i = new AtomicInteger(1);
//        new Thread(() -> {
//            while (true) {
//                try {
//                    Thread.sleep(1000);
//                } catch (InterruptedException e) {
//                    throw new RuntimeException(e);
//                }
//                i.set(i.get() + 1);
//                jmsTemplate.convertAndSend("dest", "euhjk" + i.toString());
//                System.out.println(Thread.currentThread().getName()+" : 메시지 전송" + i.toString());
//            }
//        }).start();
//    }



