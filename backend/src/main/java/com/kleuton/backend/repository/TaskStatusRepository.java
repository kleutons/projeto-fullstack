package com.kleuton.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kleuton.backend.entity.TaskStatus;

public interface TaskStatusRepository extends JpaRepository<TaskStatus, Integer> {
}
