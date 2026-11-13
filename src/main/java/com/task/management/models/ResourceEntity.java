package com.task.management.models;

import java.time.Instant;

public class ResourceEntity {
    private final long id;
    private String name;
    private String email;
    private String role;
    private final Instant createdAt;

    public ResourceEntity(long id, String name, String email, String role) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.role = role;
        this.createdAt = Instant.now();
    }

    public long getId() { return id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
    public Instant getCreatedAt() { return createdAt; }
}