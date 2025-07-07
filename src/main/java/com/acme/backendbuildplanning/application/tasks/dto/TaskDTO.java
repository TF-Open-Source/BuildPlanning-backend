package com.acme.backendbuildplanning.application.tasks.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class TaskDTO {
    private Long id;
    private String nombre;
    private String descripcion;
    private String fechaInicio;
    private String fechaLimite;
    private Integer prioridad;
    private String estado;
    private AssignedToDTO assignedTo;
    private Boolean hasIssue;
    private String issueDescription;

    @Data
    public static class AssignedToDTO {
        private Long id;
        private String email;
    }
}

