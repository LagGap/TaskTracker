package tasktracker.model;

import java.time.LocalDateTime;
import java.util.Map;

public class Task {
    private static int lastGeneratedId = 0;

    private int id;
    private String description;
    private Status status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Task(int id, String description, Status status, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.description = description;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Task(String description) {
        this.id = lastGeneratedId++;
        this.description = description;
        this.status = Status.TO_DO;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public Task() {
        this.id = 0;
        this.description = null;
        this.status = null;
        this.createdAt = null;
        this.updatedAt = null;
    }

    public int getId() {
        return id;
    }

    public Status getStatus() {
        return status;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public static void setLastGeneratedId(Map<Integer, Task> map) {
        lastGeneratedId = map.size();
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String toJsonString() {
        return "{" + "\"id\":" + this.id + "," + "\"description\":" + "\"" + this.description + "\"" + "," + "\"status\":" + Status.fromStatus(this.status) + ","
                + "\"updatedAt\":" + "\"" + this.updatedAt + "\"" + "," + "\"createdAt\":" + "\"" + this.updatedAt + "\""
                + "}";
    }
}