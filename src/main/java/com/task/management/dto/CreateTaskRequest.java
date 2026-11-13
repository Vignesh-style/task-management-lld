// src/main/java/com/example/taskmanager/api/dto/CreateTaskRequest.java
package com.task.management.dto;

import jakarta.validation.constraints.NotBlank;

public class CreateTaskRequest {
    @NotBlank
    public String title;
    public String description;
    public String priority; // LOW / MEDIUM / HIGH
}
