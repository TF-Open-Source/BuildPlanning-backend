package com.acme.backendbuildplanning.application.tasks.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;


@Entity
@Getter
@Setter
@Table(name = "tarea")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "titulo")
    private String title;

    @Column(name = "descripcion")
    private String description;

    @Column(name = "fecha_inicio")
    private LocalDate startDate;

    @Column(name = "fecha_fin")
    private LocalDate endDate;

    @Column(name = "prioridad")
    private String priority;

    @Column(name = "estado_id")
    private Long statusId;

    @Column(name = "asignado_a_id")
    private Long assignedToId;

    @Column(name = "creada_por_id")
    private Long createdById;

}
