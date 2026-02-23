package com.todo.app.service;

import com.todo.app.repository.TodoRepository;
import com.todo.app.model.TodoItem;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TodoService {

        private final TodoRepository todoRepository;

        public TodoService(TodoRepository todoRepository) {
            this.todoRepository = todoRepository;
        }

        public List<TodoItem> getAllTodoItems() {
            return todoRepository.findAll();
        }

        public Optional<TodoItem> getTodoItemById(String id) {
            return todoRepository.findById(id);
        }

        public TodoItem createTodo(TodoItem todoItem) {
            return todoRepository.save(todoItem);
        }

        public boolean deleteTodoItem(String id) {
            return todoRepository.delete(id);
        }

        public TodoItem updateTodoItem(String id, TodoItem updatedItem) {
            return todoRepository.update(id, updatedItem);
        }

        public void clearAll() {
            todoRepository.clear();
        }

        public int count() {
            return todoRepository.count();
        }
}
