package org.broker.product.activemq.publisher;

import lombok.Getter;
import org.broker.model.Publisher;

import java.util.HashMap;
import java.util.Map;

@Getter
public class ActiveMQPublisher implements Publisher {
    private final String source;
    private final String topic;
    private Map<String, Object> policyMap = new HashMap<>();

    public ActiveMQPublisher(String source, String topic) {
        this.source = source;
        this.topic = topic;
    }

    public void setPolicyMap(String key, Object value){
        policyMap.put(key,value);
    }
}

