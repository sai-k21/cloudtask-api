package com.cloudtask;

import com.cloudtask.dto.TaskRequest;
import com.cloudtask.dto.TaskResponse;
import com.cloudtask.entity.Task;
import com.cloudtask.exception.ResourceNotFoundException;
import com.cloudtask.repository.TaskRepository;
import com.cloudtask.service.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock private TaskRepository taskRepository;
    @InjectMocks private TaskService taskService;

    private Task sampleTask;
    private TaskRequest sampleRequest;

    @BeforeEach
    void setUp() {
        sampleTask = Task.builder()
                .id(1L).title("Test Task")
                .description("Test Description")
                .status(Task.TaskStatus.TODO)
                .priority(Task.TaskPriority.HIGH)
                .assignedTo("john@example.com")
                .build();

        sampleRequest = new TaskRequest();
        sampleRequest.setTitle("Test Task");
        sampleRequest.setDescription("Test Description");
        sampleRequest.setStatus(Task.TaskStatus.TODO);
        sampleRequest.setPriority(Task.TaskPriority.HIGH);
        sampleRequest.setAssignedTo("john@example.com");
    }

    @Test
    void createTask_ShouldReturnTaskResponse() {
        when(taskRepository.save(any(Task.class))).thenReturn(sampleTask);
        TaskResponse response = taskService.createTask(sampleRequest);
        assertNotNull(response);
        assertEquals("Test Task", response.getTitle());
        assertEquals(Task.TaskStatus.TODO, response.getStatus());
        verify(taskRepository, times(1)).save(any(Task.class));
    }

    @Test
    void getTaskById_WhenExists_ShouldReturnTask() {
        when(taskRepository.findById(1L)).thenReturn(Optional.of(sampleTask));
        TaskResponse response = taskService.getTaskById(1L);
        assertNotNull(response);
        assertEquals(1L, response.getId());
    }

    @Test
    void getTaskById_WhenNotExists_ShouldThrowException() {
        when(taskRepository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> taskService.getTaskById(99L));
    }

    @Test
    void deleteTask_WhenNotExists_ShouldThrowException() {
        when(taskRepository.existsById(99L)).thenReturn(false);
        assertThrows(ResourceNotFoundException.class, () -> taskService.deleteTask(99L));
    }
}
