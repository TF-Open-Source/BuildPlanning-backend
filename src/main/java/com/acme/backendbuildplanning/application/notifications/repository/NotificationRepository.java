package com.acme.backendbuildplanning.application.notifications.repository;

import com.acme.backendbuildplanning.application.notifications.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByUserId(Long userId);
    List<Notification> findByUserIdAndLeidaFalse(Long userId);
}
