package com.task.management.service;

import com.task.management.models.TaskEntity;
import com.task.management.models.TaskStatus;
import com.task.management.repo.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class TaskService {
    private final TaskRepository repo;
    private final ResourceService resourceService;
    private final LogService logService;

    public TaskService(TaskRepository repo, ResourceService resourceService, LogService logService) {
        this.repo = repo;
        this.resourceService = resourceService;
        this.logService = logService;
    }

    // create task
    public TaskEntity create(String title, String description, String priority) {
        TaskEntity t = repo.create(title, description, priority);
        logService.logUpdate(t.getId(), null, t.getStatus().name(), "Task created");
        return t;
    }

    // assign resource
    public TaskEntity assignResource(long taskId, long resourceId) {
        TaskEntity t = repo.findById(taskId).orElseThrow(() -> new RuntimeException("Task not found: " + taskId));
        resourceService.findOrThrow(resourceId); // validate exists

        String old = t.getAssignedResourceId().map(Object::toString).orElse(null);
        t.setAssignedResourceId(resourceId);
        logService.logAssign(taskId, old, String.valueOf(resourceId), "Assigned resource");
        return t;
    }

    public TaskEntity unassignResource(long taskId) {
        TaskEntity t = repo.findById(taskId).orElseThrow(() -> new RuntimeException("Task not found: " + taskId));
        String old = t.getAssignedResourceId().map(Object::toString).orElse(null);
        t.unassign(); //in memory update by reference.
        logService.logUnassign(taskId, old, null, "Unassigned resource");
        return t;
    }

    // change status with validation
    public TaskEntity changeStatus(long taskId, TaskStatus newStatus, boolean allowDirectComplete) {
        TaskEntity t = repo.findById(taskId).orElseThrow(() -> new RuntimeException("Task not found: " + taskId));
        TaskStatus old = t.getStatus();

        if (old == newStatus) return t;

        // allowed transitions (strict)
        Map<TaskStatus, Set<TaskStatus>> allowed = new HashMap<>();
        allowed.put(TaskStatus.PENDING, Set.of(TaskStatus.IN_PROGRESS));
        allowed.put(TaskStatus.IN_PROGRESS, Set.of(TaskStatus.COMPLETED));
        // optionally allow PENDING -> COMPLETED only if allowed flag
        if (allowDirectComplete) {
            allowed.computeIfAbsent(TaskStatus.PENDING, s -> new HashSet<>()).add(TaskStatus.COMPLETED);
        }

        boolean ok = allowed.getOrDefault(old, Collections.emptySet()).contains(newStatus);
        if (!ok) throw new RuntimeException("Transition not allowed: " + old + " -> " + newStatus);

        t.setStatus(newStatus);
        logService.logStatusChange(taskId, old.name(), newStatus.name(), "Status changed");
        return t;
    }

    public List<TaskEntity> listAll() { return repo.findAll(); }

    public TaskEntity get(long id) { return repo.findById(id).orElseThrow(() -> new RuntimeException("Task not found: " + id)); }

    public List<TaskEntity> getByResource(long resourceId) { return repo.findByResourceId(resourceId); }

    //todo master piece.
    public Map<String, Long> statusSummary() {
        return repo.findAll().stream()
                .collect(Collectors.groupingBy(t -> t.getStatus().name(), Collectors.counting()));
    }
}
