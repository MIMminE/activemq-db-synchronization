package org.broker.product.activemq.consumer.policy.validate;

import lombok.Data;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

//@Configuration
@ConditionalOnProperty(name = "config.activemq.policy", havingValue = "validate")
@ConfigurationProperties(prefix = "config.consumer.validate")
@Data
public class ActiveMQConsumerValidateProperties {
    private List<Sample> sample;

    @Data
    public static class Sample {
        String destination;

        public Sample(String destination) {
            this.destination = destination;
        }
    }

    public void setList(List<Sample> list){
        sample = list;
    }
}