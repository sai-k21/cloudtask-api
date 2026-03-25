package com.cloudtask.repository;

import com.cloudtask.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByStatus(Task.TaskStatus status);
    List<Task> findByPriority(Task.TaskPriority priority);
    List<Task> findByAssignedTo(String assignedTo);
    List<Task> findByStatusAndPriority(Task.TaskStatus status, Task.TaskPriority priority);

    long countByStatus(Task.TaskStatus status);
    long countByPriority(Task.TaskPriority priority);

    @Query("SELECT t FROM Task t WHERE t.dueDate < :now AND t.status NOT IN ('DONE','CANCELLED')")
    List<Task> findOverdueTasks(LocalDateTime now);

    @Query("SELECT COUNT(t) FROM Task t WHERE t.dueDate < :now AND t.status NOT IN ('DONE','CANCELLED')")
    long countOverdueTasks(LocalDateTime now);
}
