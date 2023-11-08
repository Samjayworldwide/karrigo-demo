package com.decagon.karrigobe;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-tests.properties")
@ActiveProfiles("integration-tests")
class KarriGoBeApplicationTests {

    @Test
    void contextLoads() {
    }

}
