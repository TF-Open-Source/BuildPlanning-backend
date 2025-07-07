package com.acme.backendbuildplanning.application.workers.repository;

import com.acme.backendbuildplanning.application.workers.model.Worker;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkerRepository extends JpaRepository<Worker, Long> {
}
