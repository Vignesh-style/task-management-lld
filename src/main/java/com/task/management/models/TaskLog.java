package com.task.management.models;

import java.time.Instant;

public class TaskLog {
    private final long id;
    private final long taskId;
    private final String eventType; // STATUS_CHANGE, ASSIGN, UNASSIGN, UPDATE
    private final String oldValue;
    private final String newValue;
    private final String message;
    private final Instant createdAt;

    public TaskLog(long id, long taskId, String eventType, String oldValue, String newValue, String message) {
        this.id = id;
        this.taskId = taskId;
        this.eventType = eventType;
        this.oldValue = oldValue;
        this.newValue = newValue;
        this.message = message;
        this.createdAt = Instant.now();
    }

    public long getId() { return id; }
    public long getTaskId() { return taskId; }
    public String getEventType() { return eventType; }
    public String getOldValue() { return oldValue; }
    public String getNewValue() { return newValue; }
    public String getMessage() { return message; }
    public Instant getCreatedAt() { return createdAt; }
}
