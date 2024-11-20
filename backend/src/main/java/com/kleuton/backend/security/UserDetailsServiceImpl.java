package com.kleuton.backend.security;

import java.util.Collections;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.kleuton.backend.entity.User;
import com.kleuton.backend.repository.UserRepository;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> userExisted = userRepository.findByEmail(email);
        if (userExisted.isEmpty())
            throw new UsernameNotFoundException("Não foi possível encontrar usuário com o email = " + email);

        User user = userExisted.get();
        return new org.springframework.security.core.userdetails.User(
                email,
                user.getPassword(),
                // Define, de forma estatica, o perfil do usuario encontrado
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
    }
}
