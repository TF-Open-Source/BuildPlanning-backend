package com.acme.backendbuildplanning.application.tasks.repository;

import com.acme.backendbuildplanning.application.tasks.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
}
