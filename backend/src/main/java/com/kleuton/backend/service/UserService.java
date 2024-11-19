package com.kleuton.backend.service;

import com.kleuton.backend.entity.User;
import com.kleuton.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public User getById(Integer id) {
        return userRepository.findById(id).orElse(null);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    public User saveUser(User user) {
        if (user.getId() == null || user.getId() == 0) {
            // Novo usuário, será salvo e o ID gerado automaticamente
            return userRepository.save(user);
        } else {
            User existingUser = this.getById(user.getId());
            if (existingUser != null) {
                // Atualizar usuário existente
                existingUser.setName(user.getName());
                existingUser.setEmail(user.getEmail());
                existingUser.setPassword(user.getPassword());
                existingUser.setRole(user.getRole());
                return userRepository.save(existingUser);
            } else {
                // ID fornecido não corresponde a nenhum usuário existente
                return null;
            }
        }
    }

    public Boolean deleteUser(Integer id) {
        User existingUser = this.getById(id);
        if (existingUser != null) {
            userRepository.delete(existingUser);
            return true;
        } else {
            return false;
        }
    }

}
