package com.kleuton.backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.kleuton.backend.entity.Task;
import com.kleuton.backend.repository.TaskRepository;

@Service
public class TaskService {
    @Autowired
    TaskRepository repository;

    public List<Task> getAll() {
        return repository.findAll();
    }

    public Task getById(Integer id) {
        return repository.findById(id).orElse(null);
    }

    public Task save(Task task) {
        try {
            if (task.getId() == null || task.getId() == 0) {
                // Salve New
                return repository.save(task);
            } else {
                Task existing = this.getById(task.getId());
                if (existing != null) {
                    // Update
                    existing.setProject(task.getProject());
                    existing.setResource(task.getResource());
                    existing.setTaskStatus(task.getTaskStatus());
                    existing.setDescription(task.getDescription());
                    existing.setEnd_date(task.getEnd_date());
                    existing.setStart_date(task.getStart_date());
                    existing.setTitle(task.getTitle());

                    return repository.save(existing);
                } else {
                    // ID fornecido não corresponde a nenhum usuário existente
                    throw new IllegalArgumentException("Resource not found.");
                }
            }
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("Data integrity violation. Please check your input.");
        }
    }

    public Boolean delete(Integer id) {
        Task existing = this.getById(id);
        if (existing != null) {
            repository.delete(existing);
            return true;
        } else {
            return false;
        }
    }

}
