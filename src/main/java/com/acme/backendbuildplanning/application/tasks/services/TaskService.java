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

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public Optional<Task> getTaskById(Long id) {
        return taskRepository.findById(id);
    }

    public Task createTask(Task task) {
        return taskRepository.save(task);
    }

    public Task updateTask(Long id, Task taskDetails) {
        Task task = taskRepository.findById(id).orElseThrow(()-> new RuntimeException("Task not found with id" + id));
        task.setTitle(taskDetails.getTitle());
        task.setDescription(taskDetails.getDescription());
        task.setStartDate(taskDetails.getStartDate());
        task.setEndDate(taskDetails.getEndDate());
        task.setPriority(taskDetails.getPriority());
        task.setStatusId(taskDetails.getStatusId());
        task.setAssignedToId(taskDetails.getAssignedToId());
        task.setCreatedById(taskDetails.getCreatedById());
        return taskRepository.save(task);
    }

    public void deleteTask(Long id) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new RuntimeException("Task not found with id" + id));
        taskRepository.delete(task);
    }
}
