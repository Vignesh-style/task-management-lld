package com.task.management.repo;

import com.task.management.models.TaskEntity;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Repository
public class TaskRepository {
    private final Map<Long, TaskEntity> store = new ConcurrentHashMap<>();
    private final InMemoryIdGenerator idGen = new InMemoryIdGenerator();

    public TaskEntity create(String title, String description, String priority) {
        long id = idGen.next();
        TaskEntity t = new TaskEntity(id, title, description, priority);
        store.put(id, t);
        return t;
    }

    public Optional<TaskEntity> findById(long id) { return Optional.ofNullable(store.get(id)); }
    public List<TaskEntity> findAll() { return new ArrayList<>(store.values()); }
    public void delete(long id) { store.remove(id); }

    public List<TaskEntity> findByResourceId(long resourceId) {
        return store.values().stream()
                .filter(t -> t.getAssignedResourceId().isPresent() && t.getAssignedResourceId().get() == resourceId)
                .collect(Collectors.toList());
    }

    public List<TaskEntity> findByStatus(String status) {
        return store.values().stream()
                .filter(t -> t.getStatus().name().equalsIgnoreCase(status))
                .collect(Collectors.toList());
    }
}
