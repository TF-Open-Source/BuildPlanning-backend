package com.acme.backendbuildplanning.application.notifications.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name= "notificacion")
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "usuario_id", nullable = false)
    private Long userId;

    @Column(name = "mensaje", nullable = false)
    private String mensaje;

    @Column(name = "fecha_creacion", nullable = false)
    private LocalDateTime fechaCreacion;

    @Column(name = "leida")
    private Boolean leida = false;

    @Column(name = "tipo")
    @Enumerated(EnumType.STRING)
    private TipoNotificacion tipo;

    @Column(name = "tarea_id")
    private Long tareaId; // Referencia a la tarea asociada

    public enum TipoNotificacion {
        TASK_ASSIGNMENT,
        TASK_UPDATE,
        ISSUE_REPORTED
    }
}
