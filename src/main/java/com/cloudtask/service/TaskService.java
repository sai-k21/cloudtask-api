package com.cloudtask.service;

import com.cloudtask.dto.TaskMetrics;
import com.cloudtask.dto.TaskRequest;
import com.cloudtask.dto.TaskResponse;
import com.cloudtask.entity.Task;
import com.cloudtask.exception.ResourceNotFoundException;
import com.cloudtask.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class TaskService {

    private final TaskRepository taskRepository;

    @Transactional
    public TaskResponse createTask(TaskRequest request) {
        log.info("Creating task with title: {}", request.getTitle());
        Task task = Task.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .status(request.getStatus())
                .priority(request.getPriority())
                .assignedTo(request.getAssignedTo())
                .dueDate(request.getDueDate())
                .build();
        Task saved = taskRepository.save(task);
        log.info("Task created with id: {}", saved.getId());
        return TaskResponse.from(saved);
    }

    public List<TaskResponse> getAllTasks() {
        return taskRepository.findAll().stream()
                .map(TaskResponse::from).collect(Collectors.toList());
    }

    public TaskResponse getTaskById(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + id));
        return TaskResponse.from(task);
    }

    public List<TaskResponse> getTasksByStatus(Task.TaskStatus status) {
        return taskRepository.findByStatus(status).stream()
                .map(TaskResponse::from).collect(Collectors.toList());
    }

    public List<TaskResponse> getTasksByPriority(Task.TaskPriority priority) {
        return taskRepository.findByPriority(priority).stream()
                .map(TaskResponse::from).collect(Collectors.toList());
    }

    public List<TaskResponse> getTasksByAssignee(String assignedTo) {
        return taskRepository.findByAssignedTo(assignedTo).stream()
                .map(TaskResponse::from).collect(Collectors.toList());
    }

    public List<TaskResponse> getOverdueTasks() {
        return taskRepository.findOverdueTasks(LocalDateTime.now()).stream()
                .map(TaskResponse::from).collect(Collectors.toList());
    }

    @Transactional
    public TaskResponse updateTask(Long id, TaskRequest request) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + id));
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setStatus(request.getStatus());
        task.setPriority(request.getPriority());
        task.setAssignedTo(request.getAssignedTo());
        task.setDueDate(request.getDueDate());
        Task updated = taskRepository.save(task);
        log.info("Task updated: {}", updated.getId());
        return TaskResponse.from(updated);
    }

    @Transactional
    public TaskResponse updateTaskStatus(Long id, Task.TaskStatus status) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + id));
        task.setStatus(status);
        return TaskResponse.from(taskRepository.save(task));
    }

    @Transactional
    public void deleteTask(Long id) {
        if (!taskRepository.existsById(id)) {
            throw new ResourceNotFoundException("Task not found with id: " + id);
        }
        taskRepository.deleteById(id);
        log.info("Task deleted: {}", id);
    }

    public TaskMetrics getMetrics() {
        return TaskMetrics.builder()
                .totalTasks(taskRepository.count())
                .todoCount(taskRepository.countByStatus(Task.TaskStatus.TODO))
                .inProgressCount(taskRepository.countByStatus(Task.TaskStatus.IN_PROGRESS))
                .inReviewCount(taskRepository.countByStatus(Task.TaskStatus.IN_REVIEW))
                .doneCount(taskRepository.countByStatus(Task.TaskStatus.DONE))
                .cancelledCount(taskRepository.countByStatus(Task.TaskStatus.CANCELLED))
                .criticalCount(taskRepository.countByPriority(Task.TaskPriority.CRITICAL))
                .highPriorityCount(taskRepository.countByPriority(Task.TaskPriority.HIGH))
                .overdueCount(taskRepository.countOverdueTasks(LocalDateTime.now()))
                .build();
    }
}
