package com.kleuton.backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.kleuton.backend.entity.Project;
import com.kleuton.backend.repository.ProjectRepository;

@Service
public class ProjectService {
    @Autowired
    ProjectRepository projectRepository;

    public List<Project> getAll() {
        return projectRepository.findAll();
    }

    public Project getById(Integer id) {
        return projectRepository.findById(id).orElse(null);
    }

    public Project save(Project project) {

        try {
            if (project.getId() == null || project.getId() == 0) {
                // Salve New
                return projectRepository.save(project);
            } else {
                Project existing = this.getById(project.getId());
                if (existing != null) {
                    // Update
                    existing.setManagers(project.getManagers());
                    existing.setDescription(project.getDescription());
                    existing.setStart_date(project.getStart_date());
                    existing.setEnd_date(project.getEnd_date());
                    existing.setName(project.getName());
                    existing.setStatus(project.getStatus());

                    return projectRepository.save(existing);
                } else {
                    // ID fornecido não corresponde a nenhum usuário existente
                    throw new IllegalArgumentException("Project not found.");
                }
            }
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("Data integrity violation. Please check your input.");
        }
    }

    public Boolean delete(Integer id) {
        Project existing = this.getById(id);
        if (existing != null) {
            projectRepository.delete(existing);
            return true;
        } else {
            return false;
        }
    }

}
