package org.broker.product.activemq.consumer.policy;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(
        prefix = "config.activemq"
)
public class ActiveMQConsumerConfig {
    @Getter @Setter
    public String policy;
    @Getter @Setter
    public String user;
    @Getter @Setter
    public String password;
    @Getter @Setter
    public String brokerUrl;


    @ConditionalOnProperty(name = "app.config.activemq.policy", havingValue = "basic")
    public static class BasicProperties {
        @Getter @Setter
        public String policy;
    }

}
