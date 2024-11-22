package com.kleuton.backend.controller;

import java.util.Collections;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kleuton.backend.dto.AuthenticationDTO;
import com.kleuton.backend.entity.User;
import com.kleuton.backend.security.TokenService;
import com.kleuton.backend.service.UserService;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserService userService;
    @Autowired
    private TokenService tokenService;

    @PostMapping("/register")
    public ResponseEntity<Object> registerUser(@RequestBody User user, HttpServletResponse response) {
        try {
            // Save New User
            User newUser = userService.saveUser(user);
            // retun new user
            return new ResponseEntity<>(newUser, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            // Definir status de erro e retornar mensagem de erro
            return new ResponseEntity<>(Collections.singletonMap("error", e.getMessage()), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(Collections.singletonMap("error", "An unexpected error occurred."),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/login")
    public Map<String, Object> loginHandle(@RequestBody AuthenticationDTO data, HttpServletResponse response) {

        // Log
        // System.out.println("Body: " + data);

        try {
            var usernamePassword = new UsernamePasswordAuthenticationToken(data.email(), data.password());

            var auth = authenticationManager.authenticate(usernamePassword);

            var token = tokenService.generateToken((User) auth.getPrincipal());

            // Retun Token
            return Collections.singletonMap("token", token);

        } catch (AuthenticationException authExc) {
            // Log indicando que está no bloco catch
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return Collections.singletonMap("error", "Invalid Credentials");
        }
    }

}
