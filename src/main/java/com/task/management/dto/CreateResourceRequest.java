package com.task.management.dto;

import jakarta.validation.constraints.NotBlank;

public class CreateResourceRequest {
    @NotBlank
    public String name;
    public String email;
    public String role;
}
