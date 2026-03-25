package com.cloudtask.dto;

import com.cloudtask.entity.Task;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class TaskRequest {
    @NotBlank(message = "Title is required")
    private String title;
    private String description;
    @NotNull(message = "Status is required")
    private Task.TaskStatus status;
    @NotNull(message = "Priority is required")
    private Task.TaskPriority priority;
    private String assignedTo;
    private LocalDateTime dueDate;
}
