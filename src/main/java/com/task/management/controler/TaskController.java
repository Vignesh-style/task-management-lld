package com.task.management.controler;

import com.task.management.dto.ChangeStatusRequest;
import com.task.management.dto.CreateTaskRequest;
import com.task.management.models.TaskEntity;
import com.task.management.models.TaskLog;
import com.task.management.service.LogService;
import com.task.management.service.TaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {
    private final TaskService taskService;
    private final LogService logService;

    public TaskController(TaskService taskService, LogService logService) {
        this.taskService = taskService;
        this.logService = logService;
    }

    @PostMapping
    public ResponseEntity<TaskEntity> create(@Valid @RequestBody CreateTaskRequest req) {
        return ResponseEntity.ok(taskService.create(req.title, req.description, req.priority));
    }

    @GetMapping
    public ResponseEntity<List<TaskEntity>> list(
            @RequestParam(required = false) Long resourceId,
            @RequestParam(required = false) String status) {
        if (resourceId != null) return ResponseEntity.ok(taskService.getByResource(resourceId));
        if (status != null) return ResponseEntity.ok(taskService.listAll().stream()
                .filter(t -> t.getStatus().name().equalsIgnoreCase(status)).toList());
        return ResponseEntity.ok(taskService.listAll());
    }

    @PostMapping("/{taskId}/assign")
    public ResponseEntity<TaskEntity> assign(@PathVariable long taskId, @RequestParam long resourceId) {
        return ResponseEntity.ok(taskService.assignResource(taskId, resourceId));
    }

    @PostMapping("/{taskId}/unassign")
    public ResponseEntity<TaskEntity> unassign(@PathVariable long taskId) {
        return ResponseEntity.ok(taskService.unassignResource(taskId));
    }

    @PostMapping("/{taskId}/status")
    public ResponseEntity<TaskEntity> changeStatus(@PathVariable long taskId, @Valid @RequestBody ChangeStatusRequest req) {
        return ResponseEntity.ok(taskService.changeStatus(taskId, req.newStatus, req.allowDirectComplete));
    }

    @GetMapping("/{taskId}")
    public ResponseEntity<TaskEntity> get(@PathVariable long taskId) {
        return ResponseEntity.ok(taskService.get(taskId));
    }

    @GetMapping("/{taskId}/logs")
    public ResponseEntity<List<TaskLog>> logs(@PathVariable long taskId) {
        return ResponseEntity.ok(logService.getLogsForTask(taskId));
    }

    @GetMapping("/status-summary")
    public ResponseEntity<Map<String, Long>> statusSummary() {
        return ResponseEntity.ok(taskService.statusSummary());
    }
}
