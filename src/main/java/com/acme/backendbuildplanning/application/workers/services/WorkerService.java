package com.acme.backendbuildplanning.application.workers.services;

import com.acme.backendbuildplanning.application.workers.model.Worker;
import com.acme.backendbuildplanning.application.workers.repository.WorkerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WorkerService {

    private final WorkerRepository repository;

    public WorkerService(WorkerRepository repository) {
        this.repository = repository;
    }

    public List<Worker> findAll() {
        return repository.findAll();
    }

    public Optional<Worker> findById(Long id) {
        return repository.findById(id);
    }

    public Worker save(Worker worker) {
        return repository.save(worker);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
