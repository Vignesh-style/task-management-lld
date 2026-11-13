package com.task.management.service;

import com.task.management.models.TaskLog;
import com.task.management.repo.LogRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LogService {
    private final LogRepository repo;

    public LogService(LogRepository repo) { this.repo = repo; }

    public TaskLog logStatusChange(long taskId, String oldStatus, String newStatus, String message) {
        return repo.create(taskId, "STATUS_CHANGE", oldStatus, newStatus, message);
    }
    public TaskLog logAssign(long taskId, String oldResource, String newResource, String message) {
        return repo.create(taskId, "ASSIGN", oldResource, newResource, message);
    }
    public TaskLog logUnassign(long taskId, String oldResource, String newResource, String message) {
        return repo.create(taskId, "UNASSIGN", oldResource, newResource, message);
    }
    public TaskLog logUpdate(long taskId, String oldValue, String newValue, String message) {
        return repo.create(taskId, "UPDATE", oldValue, newValue, message);
    }
    public List<TaskLog> getLogsForTask(long taskId) {
        return repo.findByTaskId(taskId);
    }
}
