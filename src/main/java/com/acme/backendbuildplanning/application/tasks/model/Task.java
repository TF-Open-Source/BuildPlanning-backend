package com.acme.backendbuildplanning.application.tasks.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "tarea")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre")
    private String nombre; // nombre de la tarea (clave para traducción)

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "fecha_inicio")
    private LocalDate fechaInicio;

    @Column(name = "fecha_limite")
    private LocalDate fechaLimite;

    @Column(name = "prioridad")
    private Integer prioridad;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado")
    private Estado estado;

    @Column(name = "asignado_a_id")
    private Long assignedToId;

    @Column(name = "creada_por_id")
    private Long createdById;

    @Column(name = "asignado_por_id")
    private Long assignedById; // Nuevo campo

    @Column(name = "fecha_asignacion")
    private LocalDateTime assignedDate; // Nuevo campo

    @Column(name = "tiene_problema")
    private Boolean hasIssue;

    @Column(name = "descripcion_problema")
    private String issueDescription;

    public enum Estado {
        PENDING,
        IN_PROGRESS,
        COMPLETED
    }
}
