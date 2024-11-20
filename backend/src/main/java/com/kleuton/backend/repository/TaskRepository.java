package com.kleuton.backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kleuton.backend.entity.Task;
import com.kleuton.backend.entity.TaskStatus;

public interface TaskRepository extends JpaRepository<Task, Integer> {
    Optional<Task> findByTaskStatus(TaskStatus taskStatus);
}
