package com.todo.app.exeptions;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
class GlobalExceptionHandlerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void handleValidation_shouldReturn400WithErrorWhenTitleIsBlank() throws Exception {
        String invalidJson = "{\"title\":\"\", \"description\":\"Test Description\", \"completionDate\":\"23.02.2026\"}";

        mockMvc.perform(post("/api/todos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").exists());
    }

    @Test
    void handleValidation_shouldReturn400WithErrorWhenDescriptionIsBlank() throws Exception {
        String invalidJson = "{\"title\":\"Test Title\", \"description\":\"\", \"completionDate\":\"23.02.2026\"}";

        mockMvc.perform(post("/api/todos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").exists());
    }

    @Test
    void handleValidation_shouldReturn400WithErrorWhenCompletionDateIsNull() throws Exception {
        String invalidJson = "{\"title\":\"Test Title\", \"description\":\"Test Description\", \"completionDate\":null}";

        mockMvc.perform(post("/api/todos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").exists());
    }

    @Test
    void handleValidation_shouldReturn400WithErrorMessage() throws Exception {
        String invalidJson = "{\"title\":\"\", \"description\":\"\", \"completionDate\":null}";

        mockMvc.perform(post("/api/todos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").isNotEmpty());
    }
}
