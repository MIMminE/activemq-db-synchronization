package org.broker.product.activemq;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(
        prefix = "app.config.activemq"
)
@Getter @Setter
public class ActiveMQProperties {
    public String user;
    public String password;
    public String brokerUrl;
}

