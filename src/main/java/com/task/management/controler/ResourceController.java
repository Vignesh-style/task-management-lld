package com.task.management.controler;

import com.task.management.dto.CreateResourceRequest;
import com.task.management.models.ResourceEntity;
import com.task.management.service.ResourceService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/resources")
public class ResourceController {
    private final ResourceService service;
    public ResourceController(ResourceService service) { this.service = service; }

    @PostMapping
    public ResponseEntity<ResourceEntity> create(@Valid @RequestBody CreateResourceRequest req) {
        return ResponseEntity.ok(service.create(req.name, req.email, req.role));
    }

    @GetMapping
    public ResponseEntity<List<ResourceEntity>> list() {
        return ResponseEntity.ok(service.listAll());
    }
}
