package org.broker.product.activemq.publisher.policy.validate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "app.config.activemq.publisher.validate")
@Data
public class ActiveMQPublisherValidateProperties {
    private List<Sample> sample;

    @AllArgsConstructor
    @Data
    public static class Sample {
        String source;
        String topic;

    }
}