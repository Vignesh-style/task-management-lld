package com.task.management.repo;

import com.task.management.models.TaskLog;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Repository
public class LogRepository {
    private final Map<Long, TaskLog> store = new ConcurrentHashMap<>();
    private final InMemoryIdGenerator idGen = new InMemoryIdGenerator();

    public TaskLog create(long taskId, String eventType, String oldValue, String newValue, String message) {
        long id = idGen.next();
        TaskLog log = new TaskLog(id, taskId, eventType, oldValue, newValue, message);
        store.put(id, log);
        return log;
    }

    public List<TaskLog> findByTaskId(long taskId) {
        return store.values().stream()
                .filter(l -> l.getTaskId() == taskId)
                .sorted(Comparator.comparing(TaskLog::getCreatedAt))
                .collect(Collectors.toList());
    }

    public List<TaskLog> findAll() {
        return new ArrayList<>(store.values());
    }
}
