package com.acme.backendbuildplanning.application.tasks.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class TaskDTO {
    private Long id;
    private String title;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private String priority;
    private Long statusId;
    private Long assignedToId;
    private Long createdById;

    public enum TaskStatus {
        PENDING,IN_PROGRESS,COMPLETED
    }
}
