package com.task.management.repo;

import com.task.management.models.ResourceEntity;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class ResourceRepository {
    private final Map<Long, ResourceEntity> store = new ConcurrentHashMap<>();
    private final InMemoryIdGenerator idGen = new InMemoryIdGenerator();

    public ResourceEntity save(ResourceEntity resource) {
        store.put(resource.getId(), resource);
        return resource;
    }

    public ResourceEntity create(String name, String email, String role) {
        long id = idGen.next();
        ResourceEntity r = new ResourceEntity(id, name, email, role);
        return save(r);
    }

    public Optional<ResourceEntity> findById(long id) { return Optional.ofNullable(store.get(id)); }
    public List<ResourceEntity> findAll() { return new ArrayList<>(store.values()); }
    public void delete(long id) { store.remove(id); }
}
