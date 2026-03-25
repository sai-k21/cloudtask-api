package com.cloudtask.dto;

import com.cloudtask.entity.Task;
import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

@Data @Builder
public class TaskResponse {
    private Long id;
    private String title;
    private String description;
    private Task.TaskStatus status;
    private Task.TaskPriority priority;
    private String assignedTo;
    private LocalDateTime dueDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static TaskResponse from(Task task) {
        return TaskResponse.builder()
                .id(task.getId()).title(task.getTitle())
                .description(task.getDescription()).status(task.getStatus())
                .priority(task.getPriority()).assignedTo(task.getAssignedTo())
                .dueDate(task.getDueDate()).createdAt(task.getCreatedAt())
                .updatedAt(task.getUpdatedAt()).build();
    }
}
