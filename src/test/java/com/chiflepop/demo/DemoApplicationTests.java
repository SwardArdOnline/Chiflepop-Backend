package com.chiflepop.demo;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DemoApplicationTests {

    @Disabled("Requiere MySQL encendido")
    @Test
    void contextLoads() {
    }

}