package com.kleuton.backend.controller;

import com.kleuton.backend.entity.User;
import com.kleuton.backend.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    // List all users
    @GetMapping
    @Operation(summary = "List all users", description = "Retrieve a list of all users.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Request successfully executed",
            content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = User.class, example = "[{\"id\": 1, \"name\": \"João Silva\", \"email\": \"joao.silva@example.com\"}]"))),
        @ApiResponse(responseCode = "204", description = "No content available",
            content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error",
            content = @Content(mediaType = "application/json",
            schema = @Schema(example = "{\"error\": \"An unexpected error occurred.\"}")))
    })
    public ResponseEntity<List<User>> getAll() {
        List<User> users = userService.getAll();
        if (!users.isEmpty())
            return new ResponseEntity<>(users, HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Get user by ID
    @GetMapping("/{id}")
    @Operation(summary = "Get user by ID", description = "Retrieve a specific user by their ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Request successfully executed",
            content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = User.class, example = "{\"id\": 1, \"name\": \"João Silva\", \"email\": \"joao.silva@example.com\"}"))),
        @ApiResponse(responseCode = "400", description = "Invalid request",
            content = @Content(mediaType = "application/json",
            schema = @Schema(example = "{\"error\": \"Invalid request data.\"}"))),
        @ApiResponse(responseCode = "403", description = "You do not have permission for this request",
            content = @Content(mediaType = "application/json",
            schema = @Schema(example = "{\"error\": \"Access forbidden.\"}"))),
        @ApiResponse(responseCode = "404", description = "Resource not found",
            content = @Content(mediaType = "application/json",
            schema = @Schema(example = "{\"error\": \"User not found.\"}"))),
        @ApiResponse(responseCode = "500", description = "Internal server error",
            content = @Content(mediaType = "application/json",
            schema = @Schema(example = "{\"error\": \"An unexpected error occurred.\"}")))
    })
    public ResponseEntity<Object> getById(@PathVariable Integer id, HttpServletResponse response) {
        try {
            User user = userService.getById(id);
            if (user != null) {
                return new ResponseEntity<>(user, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(Collections.singletonMap("error", "User not found."), HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(Collections.singletonMap("error", "An unexpected error occurred."),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Create new user
    @PostMapping
    @Operation(summary = "Create a new user", description = "Create a new user in the system.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "User successfully created",
            content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = User.class, example = "{\"id\": 1, \"name\": \"João Silva\", \"email\": \"joao.silva@example.com\"}"))),
        @ApiResponse(responseCode = "400", description = "Invalid request data",
            content = @Content(mediaType = "application/json",
            schema = @Schema(example = "{\"error\": \"Invalid request data.\"}"))),
        @ApiResponse(responseCode = "500", description = "Internal server error",
            content = @Content(mediaType = "application/json",
            schema = @Schema(example = "{\"error\": \"An unexpected error occurred.\"}")))
    })
    public ResponseEntity<Object> createUser(@RequestBody User user) {
        try {
            User newUser = userService.saveUser(user);
            return new ResponseEntity<>(newUser, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(Collections.singletonMap("error", e.getMessage()), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(Collections.singletonMap("error", "An unexpected error occurred."),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Update existing user
    @PutMapping("/{id}")
    @Operation(summary = "Update an existing user", description = "Update the details of an existing user.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User successfully updated",
            content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = User.class, example = "{\"id\": 1, \"name\": \"João Silva\", \"email\": \"joao.silva@example.com\"}"))),
        @ApiResponse(responseCode = "400", description = "Invalid request data",
            content = @Content(mediaType = "application/json",
            schema = @Schema(example = "{\"error\": \"Invalid request data.\"}"))),
        @ApiResponse(responseCode = "404", description = "User not found",
            content = @Content(mediaType = "application/json",
            schema = @Schema(example = "{\"error\": \"User not found.\"}"))),
        @ApiResponse(responseCode = "500", description = "Internal server error",
            content = @Content(mediaType = "application/json",
            schema = @Schema(example = "{\"error\": \"An unexpected error occurred.\"}")))
    })
    public ResponseEntity<Object> updateUser(@PathVariable Integer id, @RequestBody User user,
            HttpServletResponse response) {
        try {
            user.setId(id);
            User updatedUser = userService.saveUser(user);
            if (updatedUser != null) {
                return new ResponseEntity<>(updatedUser, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(Collections.singletonMap("error", "User not found."), HttpStatus.NOT_FOUND);
            }
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(Collections.singletonMap("error", e.getMessage()), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(Collections.singletonMap("error", "An unexpected error occurred."),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Delete user
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete user", description = "Delete a user from the system.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "User successfully deleted",
            content = @Content),
        @ApiResponse(responseCode = "404", description = "User not found",
            content = @Content(mediaType = "application/json",
            schema = @Schema(example = "{\"error\": \"User not found.\"}"))),
        @ApiResponse(responseCode = "500", description = "Internal server error",
            content = @Content(mediaType = "application/json",
            schema = @Schema(example = "{\"error\": \"An unexpected error occurred.\"}")))
    })
    public ResponseEntity<Object> deleteUser(@PathVariable Integer id) {
        try {
            boolean isDeleted = userService.deleteUser(id);
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
