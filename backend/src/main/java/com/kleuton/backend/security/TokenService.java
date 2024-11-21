package com.kleuton.backend.security;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.kleuton.backend.entity.User;

@Service
public class TokenService {
    @Value("${api.security.token.secret}")
    private String secret;

    // Definindo uma constante para o issuer
    private static final String ISSUER = "aut-api";

    public String generateToken(User user) {
        try {
            Algorithm algorithm = this.hasSecret();
            return JWT.create()
                    .withIssuer(ISSUER) // Use cosnt ISSUER
                    .withSubject(user.getEmail())
                    .withExpiresAt(this.genExpirationDate())
                    .sign(algorithm);
        } catch (JWTCreationException e) {
            throw new RuntimeException("Error while generating token", e);
        }
    }

    public String validateToken(String token) {
        try {
            Algorithm algorithm = this.hasSecret();
            return JWT.require(algorithm)
                    .withIssuer(ISSUER)
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException e) {
            System.out.println("Error validate token " + e);
            return null;
        }
    }

    private Algorithm hasSecret() {
        return Algorithm.HMAC256(secret);
    }

    public Instant genExpirationDate() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}
