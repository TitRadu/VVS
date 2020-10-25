package com.example.vvsdemo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class VvsDemoApplicationTests {
    @Autowired
    private PieceController controller;


    @Test
    void contextLoads() {
        assertThat(controller).isNotNull();

    }


}
