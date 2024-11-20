package com.kleuton.backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kleuton.backend.entity.Resource;

public interface ResourceRepository extends JpaRepository<Resource, Integer> {
    Optional<Resource> findByName(String name);
}
