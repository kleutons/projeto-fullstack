package com.kleuton.backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.kleuton.backend.entity.TaskStatus;
import com.kleuton.backend.repository.TaskStatusRepository;

@Service
public class TaskStatusService {
    @Autowired
    TaskStatusRepository repository;

    public List<TaskStatus> getAll() {
        return repository.findAll();
    }

    public TaskStatus getById(Integer id) {
        return repository.findById(id).orElse(null);
    }

    public TaskStatus save(TaskStatus taskStatus) {
        try {
            if (taskStatus.getId() == null || taskStatus.getId() == 0) {
                // Salve New
                return repository.save(taskStatus);
            } else {
                TaskStatus existing = this.getById(taskStatus.getId());
                if (existing != null) {
                    // Update
                    existing.setDescription(taskStatus.getDescription());
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
        TaskStatus existing = this.getById(id);
        if (existing != null) {
            repository.delete(existing);
            return true;
        } else {
            return false;
        }
    }

}
