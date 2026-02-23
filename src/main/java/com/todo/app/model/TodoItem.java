package com.todo.app.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.UUID;

public class TodoItem {

    private String id;

    @NotBlank(message = "Title is mandatory")
    private String title;

    @NotBlank(message = "Description is mandatory")
    private String description;

    @NotNull(message = "Completion date is mandatory")
    @JsonFormat(pattern = "dd.MM.yyyy")
    private LocalDate completionDate;

    public TodoItem() {
        this.id = UUID.randomUUID().toString();
    }
    public TodoItem(String title, String description, LocalDate completionDate) {
        this.id = UUID.randomUUID().toString();
        this.title = title;
        this.description = description;
        this.completionDate = completionDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getCompletionDate() {
        return completionDate;
    }

    public void setCompletionDate(LocalDate completionDate) {
        this.completionDate = completionDate;
    }
}
