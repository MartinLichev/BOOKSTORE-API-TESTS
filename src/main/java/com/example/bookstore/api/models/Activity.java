package com.example.bookstore.api.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Activity {
    private Integer id;
    private String title;
    private String dueDate;
    private Boolean completed;

    public Activity() {}

    public Activity(Integer id, String title, String dueDate, Boolean completed) {
        this.id = id;
        this.title = title;
        this.dueDate = dueDate;
        this.completed = completed;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDueDate() { return dueDate; }
    public void setDueDate(String dueDate) { this.dueDate = dueDate; }

    public Boolean getCompleted() { return completed; }
    public void setCompleted(Boolean completed) { this.completed = completed; }
}
