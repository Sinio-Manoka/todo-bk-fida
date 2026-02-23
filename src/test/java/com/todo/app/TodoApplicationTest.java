package com.todo.app;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThatCode;

@SpringBootTest
class TodoApplicationTest {

    @Test
    void contextLoads() {
        assertThatCode(() -> {
        }).doesNotThrowAnyException();
    }
}
