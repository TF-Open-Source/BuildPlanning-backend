package com.acme.backendbuildplanning.application.notifications.controller;

import com.acme.backendbuildplanning.application.notifications.model.Notification;
import com.acme.backendbuildplanning.exception.UserNotFoundException;
import com.acme.backendbuildplanning.application.notifications.dto.NotificationDTO;
import com.acme.backendbuildplanning.application.notifications.services.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/notifications")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    // Obtener todas las notificaciones de un usuario
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<NotificationDTO>> getNotificationsByUser(@PathVariable Long userId) {
        try {
            return ResponseEntity.ok(notificationService.getNotificationsByUser(userId));
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // Obtener notificaciones no leídas de un usuario
    @GetMapping("/user/{userId}/unread")
    public ResponseEntity<List<NotificationDTO>> getUnreadNotificationsByUser(@PathVariable Long userId) {
        try {
            return ResponseEntity.ok(notificationService.getUnreadNotificationsByUser(userId));
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // Marcar notificación como leída
    @PatchMapping("/{id}/read")
    public ResponseEntity<NotificationDTO> markAsRead(@PathVariable Long id) {
        try {
            Notification notification = notificationService.markAsRead(id);
            NotificationDTO dto = new NotificationDTO();
            dto.setId(notification.getId());
            dto.setUserId(notification.getUserId());
            dto.setMensaje(notification.getMensaje());
            dto.setFechaCreacion(notification.getFechaCreacion().toString());
            dto.setLeida(notification.getLeida());
            dto.setTipo(notification.getTipo().toString());
            dto.setTareaId(notification.getTareaId());
            return ResponseEntity.ok(dto);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}