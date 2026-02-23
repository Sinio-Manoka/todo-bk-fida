package com.todo.app.controller;

import com.todo.app.model.TodoItem;
import com.todo.app.service.TodoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.owasp.encoder.Encode;


import java.util.List;


@RestController
@RequestMapping("/api/todos")
public class TodoController {
    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @GetMapping
    public List<TodoItem> getAllTodoItems() {
        return todoService.getAllTodoItems();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TodoItem> getTodoItemById(@PathVariable String id) {
        return todoService.getTodoItemById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<TodoItem> createTodoItem(@Valid @RequestBody TodoItem todoItem) {
        todoItem.setTitle(Encode.forHtml(todoItem.getTitle()));
        todoItem.setDescription(Encode.forHtml(todoItem.getDescription()));
        TodoItem created = todoService.createTodo(todoItem);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTodoItem(@PathVariable String id) {
        if (todoService.deleteTodoItem(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<TodoItem> updateTodoItem(@PathVariable String id, @Valid @RequestBody TodoItem updatedItem) {
        TodoItem updated = todoService.updateTodoItem(id, updatedItem);
        if (updated != null) {
            return ResponseEntity.ok(updated);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/count")
    public ResponseEntity<Integer> countTodoItems() {
        int count = todoService.count();
        return ResponseEntity.ok(count);
    }

    @DeleteMapping("/clear")
    public ResponseEntity<Void> clearAllTodoItems() {
        todoService.clearAll();
        return ResponseEntity.noContent().build();
    }

}
