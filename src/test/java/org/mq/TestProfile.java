package org.mq;

import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Profile;

@Profile("test")
public class TestProfile {
    @Test
    void testProfilesTest(){
        System.out.println("Profiles Test Active!");
    }
}
