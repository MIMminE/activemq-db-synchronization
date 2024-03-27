package org.broker.product.activemq.consumer.policy;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(
        prefix = "app.config.activemq"
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
}
