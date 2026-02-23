package com.todo.app.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.todo.app.model.TodoItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
class TodoControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void getAllTodoItems_shouldReturnAllItems() throws Exception {
        String json = "{\"title\":\"Test Title\",\"description\":\"Test Description\",\"completionDate\":\"" + LocalDate.now().format(DATE_FORMATTER) + "\"}";
        mockMvc.perform(post("/api/todos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated());

        mockMvc.perform(get("/api/todos"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        mockMvc.perform(delete("/api/todos/clear"));
    }

    @Test
    void getAllTodoItems_shouldReturnEmptyArrayWhenNoItems() throws Exception {
        mockMvc.perform(delete("/api/todos/clear"));
        mockMvc.perform(get("/api/todos"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    void getTodoItemById_shouldReturnItemWhenExists() throws Exception {
        String json = "{\"title\":\"Test Title\",\"description\":\"Test Description\",\"completionDate\":\"" + LocalDate.now().format(DATE_FORMATTER) + "\"}";
        String response = mockMvc.perform(post("/api/todos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andReturn()
                .getResponse()
                .getContentAsString();

        String id = new ObjectMapper().readTree(response).get("id").asText();
        mockMvc.perform(get("/api/todos/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Test Title"));

        mockMvc.perform(delete("/api/todos/clear"));
    }

    @Test
    void getTodoItemById_shouldReturn404WhenNotExists() throws Exception {
        mockMvc.perform(get("/api/todos/non-existent-id"))
                .andExpect(status().isNotFound());
    }

    @Test
    void createTodoItem_shouldReturn201WhenValid() throws Exception {
        String json = "{\"title\":\"Test Title\",\"description\":\"Test Description\",\"completionDate\":\"" + LocalDate.now().format(DATE_FORMATTER) + "\"}";

        mockMvc.perform(post("/api/todos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("Test Title"));

        mockMvc.perform(delete("/api/todos/clear"));
    }

    @Test
    void createTodoItem_shouldReturn400WhenTitleIsBlank() throws Exception {
        String invalidJson = "{\"title\":\"\", \"description\":\"Test Description\", \"completionDate\":\"23.02.2026\"}";

        mockMvc.perform(post("/api/todos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createTodoItem_shouldReturn400WhenDescriptionIsBlank() throws Exception {
        String invalidJson = "{\"title\":\"Test Title\", \"description\":\"\", \"completionDate\":\"23.02.2026\"}";

        mockMvc.perform(post("/api/todos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createTodoItem_shouldReturn400WhenCompletionDateIsNull() throws Exception {
        String invalidJson = "{\"title\":\"Test Title\", \"description\":\"Test Description\", \"completionDate\":null}";

        mockMvc.perform(post("/api/todos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deleteTodoItem_shouldReturn204WhenDeleted() throws Exception {
        String json = "{\"title\":\"Test Title\",\"description\":\"Test Description\",\"completionDate\":\"" + LocalDate.now().format(DATE_FORMATTER) + "\"}";

        String response = mockMvc.perform(post("/api/todos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andReturn()
                .getResponse()
                .getContentAsString();

        String id = new ObjectMapper().readTree(response).get("id").asText();

        mockMvc.perform(delete("/api/todos/" + id))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteTodoItem_shouldReturn404WhenNotExists() throws Exception {
        mockMvc.perform(delete("/api/todos/non-existent-id"))
                .andExpect(status().isNotFound());
    }

    @Test
    void updateTodoItem_shouldReturn200WhenUpdated() throws Exception {
        String createJson = "{\"title\":\"Test Title\",\"description\":\"Test Description\",\"completionDate\":\"" + LocalDate.now().format(DATE_FORMATTER) + "\"}";

        String response = mockMvc.perform(post("/api/todos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createJson))
                .andReturn()
                .getResponse()
                .getContentAsString();

        String id = new ObjectMapper().readTree(response).get("id").asText();
        String updateJson = "{\"title\":\"Updated Title\",\"description\":\"Updated Description\",\"completionDate\":\"" + LocalDate.now().plusDays(1).format(DATE_FORMATTER) + "\"}";

        mockMvc.perform(put("/api/todos/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updateJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Updated Title"))
                .andExpect(jsonPath("$.description").value("Updated Description"));

        mockMvc.perform(delete("/api/todos/clear"));
    }

    @Test
    void updateTodoItem_shouldReturn404WhenNotExists() throws Exception {
        String updateJson = "{\"title\":\"Updated Title\",\"description\":\"Updated Description\",\"completionDate\":\"" + LocalDate.now().format(DATE_FORMATTER) + "\"}";

        mockMvc.perform(put("/api/todos/non-existent-id")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updateJson))
                .andExpect(status().isNotFound());
    }

    @Test
    void updateTodoItem_shouldReturn400WhenInvalid() throws Exception {
        String invalidJson = "{\"title\":\"\", \"description\":\"\", \"completionDate\":null}";

        mockMvc.perform(put("/api/todos/test-id")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    void countTodoItems_shouldReturnCount() throws Exception {
        mockMvc.perform(delete("/api/todos/clear"));
        String json = "{\"title\":\"Test Title\",\"description\":\"Test Description\",\"completionDate\":\"" + LocalDate.now().format(DATE_FORMATTER) + "\"}";
        mockMvc.perform(post("/api/todos").contentType(MediaType.APPLICATION_JSON).content(json));
        mockMvc.perform(post("/api/todos").contentType(MediaType.APPLICATION_JSON).content(json));

        mockMvc.perform(get("/api/todos/count"))
                .andExpect(status().isOk())
                .andExpect(content().string("2"));

        mockMvc.perform(delete("/api/todos/clear"));
    }

    @Test
    void countTodoItems_shouldReturnZeroWhenNoItems() throws Exception {
        mockMvc.perform(delete("/api/todos/clear"));

        mockMvc.perform(get("/api/todos/count"))
                .andExpect(status().isOk())
                .andExpect(content().string("0"));
    }

    @Test
    void clearAllTodoItems_shouldReturn204() throws Exception {
        mockMvc.perform(delete("/api/todos/clear"))
                .andExpect(status().isNoContent());
    }
}
