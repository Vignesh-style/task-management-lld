package com.task.management.service;

import com.task.management.models.ResourceEntity;
import com.task.management.repo.ResourceRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResourceService {
    private final ResourceRepository repo;
    public ResourceService(ResourceRepository repo) { this.repo = repo; }

    public ResourceEntity create(String name, String email, String role) {
        return repo.create(name, email, role);
    }

    public List<ResourceEntity> listAll() { return repo.findAll(); }

    public ResourceEntity findOrThrow(long id) {
        return repo.findById(id).orElseThrow(() -> new RuntimeException("Resource not found: " + id));
    }
}
