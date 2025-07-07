package com.acme.backendbuildplanning.application.notifications.dto;

import lombok.Data;

@Data
public class NotificationDTO {
    private Long id;
    private Long userId;
    private String mensaje;
    private String fechaCreacion;
    private Boolean leida;
    private String tipo;
    private Long tareaId;
}
