package com.acme.backendbuildplanning.application.notifications.services;

import com.acme.backendbuildplanning.exception.UserNotFoundException;
import com.acme.backendbuildplanning.application.notifications.dto.NotificationDTO;
import com.acme.backendbuildplanning.application.notifications.model.Notification;
import com.acme.backendbuildplanning.application.notifications.repository.NotificationRepository;
import com.acme.backendbuildplanning.domain.user_management.model.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private UserRepository userRepository;

    // Crear una notificación de asignación de tarea
    public Notification createTaskAssignmentNotification(Long userId, Long tareaId, String taskName, String assignerEmail) {
        if (!userRepository.existsById(userId)) {
            throw new UserNotFoundException("User not found with id " + userId);
        }

        Notification notification = new Notification();
        notification.setUserId(userId);
        notification.setMensaje("Te han asignado la tarea: " + taskName + " por " + assignerEmail);
        notification.setFechaCreacion(LocalDateTime.now());
        notification.setLeida(false);
        notification.setTipo(Notification.TipoNotificacion.TASK_ASSIGNMENT);
        notification.setTareaId(tareaId);

        return notificationRepository.save(notification);
    }

    // Obtener todas las notificaciones de un usuario
    public List<NotificationDTO> getNotificationsByUser(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new UserNotFoundException("User not found with id " + userId);
        }

        return notificationRepository.findByUserId(userId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Obtener notificaciones no leídas de un usuario
    public List<NotificationDTO> getUnreadNotificationsByUser(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new UserNotFoundException("User not found with id " + userId);
        }

        return notificationRepository.findByUserIdAndLeidaFalse(userId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Marcar notificación como leída
    public Notification markAsRead(Long notificationId) {
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new RuntimeException("Notification not found with id " + notificationId));
        notification.setLeida(true);
        return notificationRepository.save(notification);
    }

    // Conversión de Notification a NotificationDTO
    private NotificationDTO convertToDTO(Notification notification) {
        NotificationDTO dto = new NotificationDTO();
        dto.setId(notification.getId());
        dto.setUserId(notification.getUserId());
        dto.setMensaje(notification.getMensaje());
        dto.setFechaCreacion(notification.getFechaCreacion().toString());
        dto.setLeida(notification.getLeida());
        dto.setTipo(notification.getTipo().toString());
        dto.setTareaId(notification.getTareaId());
        return dto;
    }
}