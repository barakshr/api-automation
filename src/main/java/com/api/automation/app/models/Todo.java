package com.api.automation.app.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Todo {

    private Integer id;
    private String title;
    private Boolean doneStatus;
    private String description;

    public Todo() {
    }

    public Todo(Integer id, String title, Boolean doneStatus, String description) {
        this.id = id;
        this.title = title;
        this.doneStatus = doneStatus;
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Boolean getDoneStatus() {
        return doneStatus;
    }

    public void setDoneStatus(Boolean doneStatus) {
        this.doneStatus = doneStatus;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
