package org.mq.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ActiveProfiles;

import java.util.Properties;





@SpringBootTest
@EnableConfigurationProperties({ApplicationProperties.class, ApplicationProperties.SyncProperties.class})
class ApplicationPropertiesTest {

    @Autowired
    private ApplicationProperties properties;

    @Autowired
    private ApplicationProperties.SyncProperties syncProperties;

    @Test
    void setupProperties(){
        System.out.println(properties);
        System.out.println(syncProperties);
    }
}