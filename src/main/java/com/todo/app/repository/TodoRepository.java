package com.todo.app.repository;

import com.todo.app.model.TodoItem;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class TodoRepository {
    private final ConcurrentHashMap<String, TodoItem> todoItems = new ConcurrentHashMap<>();

    public List<TodoItem> findAll() {
        return new ArrayList<>(todoItems.values());
    }

    public Optional<TodoItem> findById(String id) {
        return Optional.ofNullable(todoItems.get(id));
    }

    public TodoItem save(TodoItem item) {
        todoItems.put(item.getId(), item);
        return item;
    }

    public boolean delete(String id) {
        return todoItems.remove(id) != null;
    }

    public void clear() {
        todoItems.clear();
    }

    public int count() {
        return todoItems.size();
    }

    public TodoItem update(String id, TodoItem updatedItem) {
        if (todoItems.containsKey(id)) {
            updatedItem.setId(id);
            todoItems.put(id, updatedItem);
            return updatedItem;
        }
        return null;
    }

}
