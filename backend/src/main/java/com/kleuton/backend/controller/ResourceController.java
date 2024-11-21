package com.kleuton.backend.controller;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kleuton.backend.entity.Resource;
import com.kleuton.backend.service.ResourceService;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/resource")
public class ResourceController {
    @Autowired
    ResourceService service;

    @GetMapping
    public ResponseEntity<List<Resource>> getAll() {
        List<Resource> resources = service.getAll();

        if (!resources.isEmpty())
            return new ResponseEntity<>(resources, HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Buscar por ID
    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable Integer id, HttpServletResponse response) {
        try {
            Resource resource = service.getById(id);
            if (resource != null) {
                return new ResponseEntity<>(resource, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(Collections.singletonMap("error", "Project not found."),
                        HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(Collections.singletonMap("error", "An unexpected error occurred."),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Save Data
    @PostMapping
    public ResponseEntity<Object> createUser(@RequestBody Resource resource) {
        try {
            Resource newData = service.save(resource);
            return new ResponseEntity<>(newData, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(Collections.singletonMap("error", e.getMessage()), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(Collections.singletonMap("error", "An unexpected error occurred."),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Update Data
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateUser(@PathVariable Integer id, @RequestBody Resource resource,
            HttpServletResponse response) {
        try {
            resource.setId(id);
            Resource newData = service.save(resource);
            if (newData != null) {
                return new ResponseEntity<>(newData, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(Collections.singletonMap("error", "Resource not found."),
                        HttpStatus.NOT_FOUND);
            }
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(Collections.singletonMap("error", e.getMessage()), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(Collections.singletonMap("error", "An unexpected error occurred."),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Delete
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable Integer id) {
        try {
            boolean isDeleted = service.delete(id);
            if (isDeleted) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>(Collections.singletonMap("error", "User not found."), HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(Collections.singletonMap("error", "An unexpected error occurred."),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
