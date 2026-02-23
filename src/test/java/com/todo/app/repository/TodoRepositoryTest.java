package com.todo.app.repository;


import com.todo.app.model.TodoItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;


public class TodoRepositoryTest {

    private TodoRepository repository;

    @BeforeEach
    void setUp() {
        repository = new TodoRepository();
    }

    @Test
    void shouldSaveAndRetrieve() {
        TodoItem item = new TodoItem("Test", "Test Description", LocalDate.now());
        repository.save(item);

        assertEquals(1, repository.count());
        assertTrue(repository.findById(item.getId()).isPresent());
    }

    @Test
    void shouldReturnAllItems() {
        repository.save(new TodoItem("Test", "Test Description", LocalDate.now()));
        repository.save(new TodoItem("Test", "Test Description", LocalDate.now()));


        assertEquals(2, repository.findAll().size());
    }

    @Test
    void shouldDeleteItem() {
        TodoItem item = new TodoItem("Test", "Test Description", LocalDate.now());
        repository.save(item);

        assertTrue(repository.delete(item.getId()));
        assertEquals(0, repository.count());
    }

    @Test
    void shouldUpdateItem() {
        TodoItem item = new TodoItem("Test", "Test Description", LocalDate.now());
        repository.save(item);

        TodoItem updated = new TodoItem("Updated", "Updated Description", LocalDate.now().plusDays(1));
        TodoItem result = repository.update(item.getId(), updated);

        assertNotNull(result);
        assertEquals("Updated", result.getTitle());
        assertEquals("Updated Description", result.getDescription());
    }

    @Test
    void shouldClearAllItems() {
        repository.save(new TodoItem("Test", "Test Description", LocalDate.now()));
        repository.save(new TodoItem("Test", "Test Description", LocalDate.now()));

        repository.clear();
        assertEquals(0, repository.count());
    }

    @Test
    void shouldReturnFalseWhenDeletingNonExistent() {
        assertFalse(repository.delete("nonexistent-id"));
    }

    @Test
    void shouldReturnEmptyOptionalWhenFindingNonExistent() {
        assertTrue(repository.findById("nonexistent-id").isEmpty());
    }

    @Test
    void shouldReturnNullWhenUpdatingNonExistentItem() {
        TodoItem updated = new TodoItem("Updated", "Updated Description", LocalDate.now().plusDays(1));
        TodoItem result = repository.update("nonexistent-id", updated);
        assertNull(result);
    }
}
