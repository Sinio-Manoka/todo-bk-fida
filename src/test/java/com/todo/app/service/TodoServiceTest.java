package com.todo.app.service;

import com.todo.app.model.TodoItem;
import com.todo.app.repository.TodoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TodoServiceTest {

    @Mock
    private TodoRepository todoRepository;

    @InjectMocks
    private TodoService todoService;

    private TodoItem testItem;

    @BeforeEach
    void setUp() {
        testItem = new TodoItem("Test Title", "Test Description", LocalDate.now());
    }

    @Test
    void getAllTodoItems_shouldReturnAllItemsFromRepository() {
        List<TodoItem> expectedItems = List.of(testItem);
        when(todoRepository.findAll()).thenReturn(expectedItems);

        List<TodoItem> result = todoService.getAllTodoItems();

        assertThat(result).isEqualTo(expectedItems);
        verify(todoRepository).findAll();
    }

    @Test
    void getTodoItemById_shouldReturnItemWhenExists() {
        String id = "test-id";
        when(todoRepository.findById(id)).thenReturn(Optional.of(testItem));

        Optional<TodoItem> result = todoService.getTodoItemById(id);

        assertThat(result).isPresent().contains(testItem);
        verify(todoRepository).findById(id);
    }

    @Test
    void getTodoItemById_shouldReturnEmptyWhenNotExists() {
        String id = "non-existent-id";
        when(todoRepository.findById(id)).thenReturn(Optional.empty());

        Optional<TodoItem> result = todoService.getTodoItemById(id);

        assertThat(result).isEmpty();
        verify(todoRepository).findById(id);
    }

    @Test
    void createTodo_shouldSaveAndReturnItem() {
        when(todoRepository.save(testItem)).thenReturn(testItem);

        TodoItem result = todoService.createTodo(testItem);

        assertThat(result).isEqualTo(testItem);
        verify(todoRepository).save(testItem);
    }

    @Test
    void deleteTodoItem_shouldReturnTrueWhenItemExists() {
        String id = "test-id";
        when(todoRepository.delete(id)).thenReturn(true);

        boolean result = todoService.deleteTodoItem(id);

        assertThat(result).isTrue();
        verify(todoRepository).delete(id);
    }

    @Test
    void deleteTodoItem_shouldReturnFalseWhenItemNotExists() {
        String id = "non-existent-id";
        when(todoRepository.delete(id)).thenReturn(false);

        boolean result = todoService.deleteTodoItem(id);

        assertThat(result).isFalse();
        verify(todoRepository).delete(id);
    }

    @Test
    void updateTodoItem_shouldReturnUpdatedItemWhenExists() {
        String id = "test-id";
        TodoItem updatedItem = new TodoItem("Updated Title", "Updated Description", LocalDate.now().plusDays(1));
        when(todoRepository.update(id, updatedItem)).thenReturn(updatedItem);

        TodoItem result = todoService.updateTodoItem(id, updatedItem);

        assertThat(result).isEqualTo(updatedItem);
        verify(todoRepository).update(id, updatedItem);
    }

    @Test
    void updateTodoItem_shouldReturnNullWhenItemNotExists() {
        String id = "non-existent-id";
        TodoItem updatedItem = new TodoItem("Updated Title", "Updated Description", LocalDate.now());
        when(todoRepository.update(id, updatedItem)).thenReturn(null);

        TodoItem result = todoService.updateTodoItem(id, updatedItem);

        assertThat(result).isNull();
        verify(todoRepository).update(id, updatedItem);
    }

    @Test
    void clearAll_shouldClearAllItems() {
        todoService.clearAll();

        verify(todoRepository).clear();
    }

    @Test
    void count_shouldReturnCountFromRepository() {
        int expectedCount = 5;
        when(todoRepository.count()).thenReturn(expectedCount);

        int result = todoService.count();

        assertThat(result).isEqualTo(expectedCount);
        verify(todoRepository).count();
    }
}
