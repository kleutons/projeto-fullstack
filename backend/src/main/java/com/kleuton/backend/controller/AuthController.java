package com.kleuton.backend.controller;

import java.util.Collections;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kleuton.backend.dto.LoginCredentials;
import com.kleuton.backend.entity.User;
import com.kleuton.backend.security.JWTUtil;
import com.kleuton.backend.service.UserService;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;
    @Autowired
    private JWTUtil jwtUtil;
    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public Map<String, Object> registerHandler(@RequestBody User user, HttpServletResponse response) {
        try {
            // Save New User
            User newUser = userService.saveUser(user);

            // Gerar JWT
            String token = jwtUtil.generateTokenWithUserData(newUser);

            // Retornando a resposta com o JWT
            return Collections.singletonMap("token", token);
        } catch (IllegalArgumentException e) {
            // Definir status de erro e retornar mensagem de erro
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return Collections.singletonMap("error", e.getMessage());
        }
    }

    @PostMapping("/login")
    public Map<String, Object> loginHandle(@RequestBody LoginCredentials body, HttpServletResponse response) {
        // Log do corpo da requisição
        // System.out.println("Body: " + body);

        try {
            // Criando o token que será usado no processo de autenticação
            UsernamePasswordAuthenticationToken authInputToken = new UsernamePasswordAuthenticationToken(
                    body.getEmail(), body.getPassword());

            // Autenticando as credenciais de login
            authenticationManager.authenticate(authInputToken);

            // Se o processo de autenticação foi concluído com sucesso, gerar o JWT
            User user = userService.findByEmail(body.getEmail());
            User usuarioResumido = new User();
            usuarioResumido.setName(user.getName());
            usuarioResumido.setEmail(user.getEmail());
            usuarioResumido.setId(user.getId());
            // Gerando o token JWT a partir dos dados do usuário
            String token = jwtUtil.generateTokenWithUserData(usuarioResumido);

            // Responde com o JWT
            return Collections.singletonMap("token", token);

        } catch (AuthenticationException authExc) {
            // Log indicando que está no bloco catch
            // System.out.println("Dentro do catch: Invalid Credentialss");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return Collections.singletonMap("error", "Invalid Credentials");
        }
    }

}
