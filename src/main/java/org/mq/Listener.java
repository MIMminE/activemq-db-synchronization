package org.mq;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class Listener {

    @JmsListener(destination = "dest")
    public void receive(String message) throws InterruptedException {
        Thread.sleep(100);
        System.out.println(Thread.currentThread().getName() + " : 메시지 전달받음 " + message);
    }
}
