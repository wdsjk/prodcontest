package ru.prodcontest.service.user;

import jakarta.transaction.Transactional;
import lombok.SneakyThrows;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import ru.prodcontest.exception.user.UserNotFoundUnauthorizedException;
import ru.prodcontest.repository.user.UserRepository;

// For Spring Security
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    @Autowired
    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @SneakyThrows(UserNotFoundUnauthorizedException.class)
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) {
        return userRepository.findByLogin(username).orElseThrow(UserNotFoundUnauthorizedException::new);
    }
}
