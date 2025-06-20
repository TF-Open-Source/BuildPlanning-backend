package com.acme.backendbuildplanning.application.tasks.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.acme.backendbuildplanning.application.tasks.model.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {
}
