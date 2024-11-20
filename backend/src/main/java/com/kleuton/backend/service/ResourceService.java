package com.kleuton.backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.kleuton.backend.entity.Resource;
import com.kleuton.backend.repository.ResourceRepository;

@Service
public class ResourceService {
    @Autowired
    ResourceRepository repository;

    public List<Resource> getAll() {
        return repository.findAll();
    }

    public Resource getById(Integer id) {
        return repository.findById(id).orElse(null);
    }

    public Resource save(Resource resource) {
        try {
            if (resource.getId() == null || resource.getId() == 0) {
                // Salve New
                return repository.save(resource);
            } else {
                Resource existing = this.getById(resource.getId());
                if (existing != null) {
                    // Update
                    existing.setRole(resource.getRole());
                    existing.setName(resource.getName());
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
        Resource existing = this.getById(id);
        if (existing != null) {
            repository.delete(existing);
            return true;
        } else {
            return false;
        }
    }

}
