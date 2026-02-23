package com.todo.app.model;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class TodoItemTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void shouldCreateWithUUID() {
        TodoItem item = new TodoItem("Test Title",
                "Test Description",
                            LocalDate.now());
        assertNotNull(item.getId());
        assertEquals("Test Title", item.getTitle());
        assertEquals("Test Description", item.getDescription());
        assertEquals(LocalDate.now(), item.getCompletionDate());
    }

    @Test
    void shouldFailValidationWhenTitleIsBlank() {
        TodoItem item = new TodoItem("",
                "Test Description",
                LocalDate.now());
        var violations = validator.validate(item);
        assertFalse(violations.isEmpty());
    }

    @Test
    void shouldFailValidationWhenDescriptionIsBlank() {
        TodoItem item = new TodoItem("Test Title",
                "",
                LocalDate.now());
        var violations = validator.validate(item);
        assertFalse(violations.isEmpty());
    }

    @Test
    void shouldFailValidationWhenCompletionDateIsNull() {
        TodoItem item = new TodoItem("Test Title",
                "Test Description",
                null);
        var violations = validator.validate(item);
        assertFalse(violations.isEmpty());
    }

    @Test
    void shouldPassValidationWithValidData() {
        TodoItem item = new TodoItem("Test Title",
                "Test Description",
                LocalDate.now());
        var violations = validator.validate(item);
        assertTrue(violations.isEmpty());
    }

    @Test
    void shouldGetId() {
        TodoItem item = new TodoItem("Test Title", "Test Description", LocalDate.now());
        assertNotNull(item.getId());
    }

    @Test
    void shouldSetId() {
        TodoItem item = new TodoItem("Test Title", "Test Description", LocalDate.now());
        String customId = "custom-id-123";
        item.setId(customId);
        assertEquals(customId, item.getId());
    }

    @Test
    void shouldGetTitle() {
        TodoItem item = new TodoItem("Test Title", "Test Description", LocalDate.now());
        assertEquals("Test Title", item.getTitle());
    }

    @Test
    void shouldSetTitle() {
        TodoItem item = new TodoItem();
        String newTitle = "New Title";
        item.setTitle(newTitle);
        assertEquals(newTitle, item.getTitle());
    }

    @Test
    void shouldGetDescription() {
        TodoItem item = new TodoItem("Test Title", "Test Description", LocalDate.now());
        assertEquals("Test Description", item.getDescription());
    }

    @Test
    void shouldSetDescription() {
        TodoItem item = new TodoItem();
        String newDescription = "New Description";
        item.setDescription(newDescription);
        assertEquals(newDescription, item.getDescription());
    }

    @Test
    void shouldGetCompletionDate() {
        LocalDate date = LocalDate.now();
        TodoItem item = new TodoItem("Test Title", "Test Description", date);
        assertEquals(date, item.getCompletionDate());
    }

    @Test
    void shouldSetCompletionDate() {
        TodoItem item = new TodoItem();
        LocalDate newDate = LocalDate.now().plusDays(5);
        item.setCompletionDate(newDate);
        assertEquals(newDate, item.getCompletionDate());
    }
}
