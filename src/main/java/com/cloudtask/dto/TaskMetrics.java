package com.cloudtask.dto;

import lombok.Builder;
import lombok.Data;

@Data @Builder
public class TaskMetrics {
    private long totalTasks;
    private long todoCount;
    private long inProgressCount;
    private long inReviewCount;
    private long doneCount;
    private long cancelledCount;
    private long criticalCount;
    private long highPriorityCount;
    private long overdueCount;
}
