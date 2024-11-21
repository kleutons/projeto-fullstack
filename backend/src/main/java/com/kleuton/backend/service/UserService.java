package com.kleuton.backend.service;

import com.kleuton.backend.entity.User;
import com.kleuton.backend.entity.UserRole;
import com.kleuton.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public User getById(Integer id) {
        return userRepository.findById(id).orElse(null);
    }

    public User saveUser(User user) {
        // Validar a senha antes de criptografar
        validatePassword(user.getPassword());

        // Criptografar a senha antes de salvar
        String encodedPass = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPass); // Setar a senha criptografada no usuário

        // Definir o papel padrão como USER, se não estiver definido
        if (user.getRole() == null) {
            user.setRole(UserRole.USER);
        }

        try {
            if (user.getId() == null || user.getId() == 0) {
                // Novo usuário, será salvo e o ID gerado automaticamente
                return userRepository.save(user);
            } else {
                User existingUser = this.getById(user.getId());
                if (existingUser != null) {
                    // Atualizar usuário existente
                    existingUser.setName(user.getName());
                    existingUser.setEmail(user.getEmail());
                    existingUser.setPassword(encodedPass); // Atualizar a senha criptografada
                    existingUser.setRole(user.getRole());
                    return userRepository.save(existingUser);
                } else {
                    // ID fornecido não corresponde a nenhum usuário existente
                    throw new IllegalArgumentException("User not found.");
                }
            }
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("Data integrity violation. Please check your input.");
        }
    }

    private void validatePassword(String password) {
        if (password == null || password.isEmpty() || password.length() < 6) {
            throw new IllegalArgumentException("Password must be at least 6 characters long.");
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
