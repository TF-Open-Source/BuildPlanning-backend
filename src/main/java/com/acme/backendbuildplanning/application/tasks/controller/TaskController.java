package com.acme.backendbuildplanning.application.tasks.controller;

import com.acme.backendbuildplanning.application.tasks.model.Task;
import com.acme.backendbuildplanning.application.tasks.services.TaskService;
import com.acme.backendbuildplanning.domain.user_management.model.repository.UserRepository;
import com.acme.backendbuildplanning.exception.TaskNotFoundException;
import com.acme.backendbuildplanning.application.tasks.dto.TaskDTO;
import com.acme.backendbuildplanning.application.tasks.dto.AssignTaskRequestDTO;
import com.acme.backendbuildplanning.exception.UnauthorizedException;
import com.acme.backendbuildplanning.exception.UserNotFoundException;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private UserRepository userRepository;
    // Obtener todas las tareas
    @GetMapping
    public List<Task> getAllTasks() {
        return taskService.getAllTasks();
    }

    // Obtener una tarea por su ID
    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long id) {
        return taskService.getTaskById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Crear una nueva tarea
    @PostMapping
    public Task createTask(@RequestBody Task task) {
        return taskService.createTask(task);
    }

    // Actualizar una tarea existente
    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable Long id, @RequestBody Task task) {
        try {
            Task updatedTask = taskService.updateTask(id, task);
            return ResponseEntity.ok(updatedTask);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Eliminar una tarea
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        try {
            taskService.deleteTask(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    //Asignar tarea a usuario
    @PatchMapping("/{id}/assign")
    public ResponseEntity<TaskDTO> assignTaskToUser(@PathVariable Long id, @RequestBody AssignTaskRequest request, @RequestHeader("X-User-Id") Long assignedById){
        try {
            Task updatedTask = taskService.assignTaskToUser(id, request.getUserId(), assignedById);
            return ResponseEntity.ok(convertToDTO(updatedTask));
        } catch (TaskNotFoundException | UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (UnauthorizedException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    private TaskDTO convertToDTO(Task task) {
        TaskDTO dto = new TaskDTO();
        dto.setId(task.getId());
        dto.setNombre(task.getNombre());
        dto.setDescripcion(task.getDescripcion());
        dto.setFechaInicio(task.getFechaInicio() != null ? task.getFechaInicio().toString() : null);
        dto.setFechaLimite(task.getFechaLimite() != null ? task.getFechaLimite().toString() : null);
        dto.setPrioridad(task.getPrioridad());
        dto.setEstado(task.getEstado().toString());
        if (task.getAssignedToId() != null) {
            TaskDTO.AssignedToDTO assignedTo = new TaskDTO.AssignedToDTO();
            assignedTo.setId(task.getAssignedToId());
            userRepository.findById(task.getAssignedToId()).ifPresent(user ->
                    assignedTo.setEmail(user.getEmail())); // Llamada a la instancia
            dto.setAssignedTo(assignedTo);
        }
        dto.setHasIssue(task.getHasIssue());
        dto.setIssueDescription(task.getIssueDescription());
        return dto;
    }

    // Conversión de TaskDTO a Task
    private Task convertToEntity(TaskDTO dto) {
        Task task = new Task();
        task.setId(dto.getId());
        task.setNombre(dto.getNombre());
        task.setDescripcion(dto.getDescripcion());
        task.setFechaInicio(LocalDate.parse(dto.getFechaInicio()));
        task.setFechaLimite(LocalDate.parse(dto.getFechaLimite()));
        task.setPrioridad(dto.getPrioridad());
        task.setEstado(Task.Estado.valueOf(dto.getEstado()));
        if (dto.getAssignedTo() != null) {
            task.setAssignedToId(dto.getAssignedTo().getId());
        }
        task.setHasIssue(dto.getHasIssue());
        task.setIssueDescription(dto.getIssueDescription());
        return task;
    }

    @Data
    static class AssignTaskRequest {
        private Long userId;
    }
}

