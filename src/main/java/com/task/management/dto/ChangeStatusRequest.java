package com.task.management.dto;

import com.task.management.models.TaskStatus;
import jakarta.validation.constraints.NotNull;

public class ChangeStatusRequest {
    @NotNull
    public TaskStatus newStatus;
    public boolean allowDirectComplete = false;
}
