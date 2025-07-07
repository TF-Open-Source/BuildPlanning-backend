package com.acme.backendbuildplanning.application.tasks.services;

import com.acme.backendbuildplanning.application.tasks.model.Task;
import com.acme.backendbuildplanning.application.tasks.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    // Listar todas las tareas
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    // Obtener una tarea por ID
    public Optional<Task> getTaskById(Long id) {
        return taskRepository.findById(id);
    }

    // Crear nueva tarea
    public Task createTask(Task task) {
        return taskRepository.save(task);
    }

    // Actualizar tarea
    public Task updateTask(Long id, Task updatedTask) {
        return taskRepository.findById(id).map(existingTask -> {
            existingTask.setNombre(updatedTask.getNombre());
            existingTask.setDescripcion(updatedTask.getDescripcion());
            existingTask.setFechaInicio(updatedTask.getFechaInicio());
            existingTask.setFechaLimite(updatedTask.getFechaLimite());
            existingTask.setPrioridad(updatedTask.getPrioridad());
            existingTask.setEstado(updatedTask.getEstado());
            existingTask.setAssignedToId(updatedTask.getAssignedToId());
            existingTask.setCreatedById(updatedTask.getCreatedById());
            existingTask.setHasIssue(updatedTask.getHasIssue());
            existingTask.setIssueDescription(updatedTask.getIssueDescription());
            return taskRepository.save(existingTask);
        }).orElseThrow(() -> new RuntimeException("Task not found with id " + id));
    }

    // Eliminar tarea
    public void deleteTask(Long id) {
        if (!taskRepository.existsById(id)) {
            throw new RuntimeException("Task not found with id " + id);
        }
        taskRepository.deleteById(id);
    }
}