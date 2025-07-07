package com.acme.backendbuildplanning.application.tasks.services;

import com.acme.backendbuildplanning.application.notifications.services.NotificationService;
import com.acme.backendbuildplanning.application.tasks.model.Task;
import com.acme.backendbuildplanning.application.tasks.repository.TaskRepository;
import com.acme.backendbuildplanning.domain.user_management.model.User;
import com.acme.backendbuildplanning.domain.user_management.model.UserType;
import com.acme.backendbuildplanning.exception.UnauthorizedException;
import com.acme.backendbuildplanning.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.acme.backendbuildplanning.domain.user_management.model.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private NotificationService notificationService;

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

    //Asignar Tarea
    public Task assignTaskToUser(Long taskId, Long userId,Long assignedById) {
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new RuntimeException("Task not found with id" + taskId));

        if(!userRepository.existsById(userId)) {
            throw new UserNotFoundException("User not found with id" + userId);
        }

        if(!hasPermissionToAssign(assignedById)){
            throw new UnauthorizedException("User does not have permission to assign tasks");
        }

        task.setAssignedToId(assignedById);
        task.setAssignedById(assignedById);
        task.setAssignedDate(LocalDateTime.now());
        task.setEstado(Task.Estado.IN_PROGRESS);

        Task savedTask = taskRepository.save(task);

        String assignerEmail = userRepository.findById(assignedById).map(User::getEmail).orElse("unknown@example.com");

        notificationService.createTaskAssignmentNotification(userId, taskId, task.getNombre(), assignerEmail);
        return taskRepository.save(task);
    }

    // Método auxiliar para verificar permisos
    private boolean hasPermissionToAssign(Long userId) {
        return userRepository.findById(userId)
                .map(user -> user.getUserType() == UserType.JEFEDEOBRAS)
                .orElse(false);
    }
}