package com.task.management.models;

import java.time.Instant;
import java.util.Optional;

public class TaskEntity {

    private final long id;
    private String title;
    private String description;
    private String priority; // LOW, MEDIUM, HIGH
    private TaskStatus status;
    private Instant createdAt;
    private Instant updatedAt;
    // current assignee resource id (Optional)
    private Long assignedResourceId;

    public TaskEntity(long id, String title, String description, String priority) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.status = TaskStatus.PENDING;
        this.createdAt = Instant.now();
        this.updatedAt = Instant.now();
        this.assignedResourceId = null;
    }

    public long getId() { return id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; touch(); }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; touch(); }
    public String getPriority() { return priority; }
    public void setPriority(String priority) { this.priority = priority; touch(); }
    public TaskStatus getStatus() { return status; }
    public void setStatus(TaskStatus status) { this.status = status; touch(); }
    public Instant getCreatedAt() { return createdAt; }
    public Instant getUpdatedAt() { return updatedAt; }
    public Optional<Long> getAssignedResourceId() { return Optional.ofNullable(assignedResourceId); }
    public void setAssignedResourceId(Long assignedResourceId) { this.assignedResourceId = assignedResourceId; touch(); }
    public void unassign() { this.assignedResourceId = null; touch(); }
    private void touch() { this.updatedAt = Instant.now(); }
}
