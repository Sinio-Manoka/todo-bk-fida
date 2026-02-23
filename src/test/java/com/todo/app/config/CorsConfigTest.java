package com.todo.app.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class CorsConfigTest {

    @Autowired
    private CorsConfig corsConfig;

    @Test
    void corsConfigurer_shouldReturnWebMvcConfigurer() {
        WebMvcConfigurer configurer = corsConfig.corsConfigurer();
        assertThat(configurer).isNotNull();
    }

    @Test
    void corsConfigurer_shouldBeBean() {
        assertThat(corsConfig).isNotNull();
    }
}
