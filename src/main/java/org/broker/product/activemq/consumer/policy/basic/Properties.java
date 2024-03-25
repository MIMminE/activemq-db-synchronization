package org.broker.product.activemq.consumer.policy.basic;

import lombok.Data;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@ConditionalOnProperty(name = "app.config.activemq.policy", havingValue = "basic")
@ConfigurationProperties(prefix = "config.consumer.basic")
@Data
public class Properties {
    String test;
}