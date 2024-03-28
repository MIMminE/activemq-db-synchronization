package org.broker.product.activemq.publisher.policy.validate;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "app.config.activemq.publisher.validate")
@Data
public class ActiveMQPublisherValidateProperties {
    private List<Sample> sample;

    @Data
    public static class Sample {
        String source;

        public Sample(String source) {
            this.source = source;
        }
    }

    public void setList(List<Sample> list){
        sample = list;
    }
}