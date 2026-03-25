package com.cloudtask.controller;

import com.cloudtask.dto.ApiResponse;
import com.cloudtask.dto.TaskMetrics;
import com.cloudtask.dto.TaskRequest;
import com.cloudtask.dto.TaskResponse;
import com.cloudtask.entity.Task;
import com.cloudtask.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tasks")
@RequiredArgsConstructor
@Tag(name = "Task Management", description = "APIs for managing tasks")
@CrossOrigin(origins = "*")
public class TaskController {

    private final TaskService taskService;

    @PostMapping
    @Operation(summary = "Create a new task")
    public ResponseEntity<ApiResponse<TaskResponse>> createTask(@Valid @RequestBody TaskRequest request) {
        TaskResponse task = taskService.createTask(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(task, "Task created successfully"));
    }

    @GetMapping
    @Operation(summary = "Get all tasks")
    public ResponseEntity<ApiResponse<List<TaskResponse>>> getAllTasks() {
        return ResponseEntity.ok(ApiResponse.success(taskService.getAllTasks(), "Tasks retrieved successfully"));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get task by ID")
    public ResponseEntity<ApiResponse<TaskResponse>> getTaskById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(taskService.getTaskById(id), "Task retrieved successfully"));
    }

    @GetMapping("/status/{status}")
    @Operation(summary = "Get tasks by status")
    public ResponseEntity<ApiResponse<List<TaskResponse>>> getTasksByStatus(@PathVariable Task.TaskStatus status) {
        return ResponseEntity.ok(ApiResponse.success(taskService.getTasksByStatus(status), "Tasks retrieved"));
    }

    @GetMapping("/priority/{priority}")
    @Operation(summary = "Get tasks by priority")
    public ResponseEntity<ApiResponse<List<TaskResponse>>> getTasksByPriority(@PathVariable Task.TaskPriority priority) {
        return ResponseEntity.ok(ApiResponse.success(taskService.getTasksByPriority(priority), "Tasks retrieved"));
    }

    @GetMapping("/assignee/{assignedTo}")
    @Operation(summary = "Get tasks by assignee")
    public ResponseEntity<ApiResponse<List<TaskResponse>>> getTasksByAssignee(@PathVariable String assignedTo) {
        return ResponseEntity.ok(ApiResponse.success(taskService.getTasksByAssignee(assignedTo), "Tasks retrieved"));
    }

    @GetMapping("/overdue")
    @Operation(summary = "Get overdue tasks")
    public ResponseEntity<ApiResponse<List<TaskResponse>>> getOverdueTasks() {
        return ResponseEntity.ok(ApiResponse.success(taskService.getOverdueTasks(), "Overdue tasks retrieved"));
    }

    @GetMapping("/metrics")
    @Operation(summary = "Get task metrics dashboard")
    public ResponseEntity<ApiResponse<TaskMetrics>> getMetrics() {
        return ResponseEntity.ok(ApiResponse.success(taskService.getMetrics(), "Metrics retrieved successfully"));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a task")
    public ResponseEntity<ApiResponse<TaskResponse>> updateTask(
            @PathVariable Long id, @Valid @RequestBody TaskRequest request) {
        return ResponseEntity.ok(ApiResponse.success(taskService.updateTask(id, request), "Task updated successfully"));
    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "Update task status only")
    public ResponseEntity<ApiResponse<TaskResponse>> updateStatus(
            @PathVariable Long id, @RequestParam Task.TaskStatus status) {
        return ResponseEntity.ok(ApiResponse.success(taskService.updateTaskStatus(id, status), "Status updated"));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a task")
    public ResponseEntity<ApiResponse<Void>> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.ok(ApiResponse.success(null, "Task deleted successfully"));
    }
}
