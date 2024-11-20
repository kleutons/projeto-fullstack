package com.kleuton.backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kleuton.backend.entity.Project;

public interface ProjectRepository extends JpaRepository<Project, Integer> {
    Optional<Project> findByDescription(String description);
}
