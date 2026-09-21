package com.acme.platform.service.impl;

import com.acme.platform.domain.User;
import com.acme.platform.repository.UserRepository;
import com.acme.platform.security.PasswordHasher;
import com.acme.platform.security.TokenService;
import com.acme.platform.service.AuthService;
import java.time.Instant;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordHasher passwordHasher;
    private final TokenService tokenService;

    public AuthServiceImpl(UserRepository userRepository,
                           PasswordHasher passwordHasher,
                           TokenService tokenService) {
        this.userRepository = userRepository;
        this.passwordHasher = passwordHasher;
        this.tokenService = tokenService;
    }

    @Override
    public User login(String email, String password) {
        User user = userRepository.findByEmail(email).orElse(null);
        if (user == null || !user.isEnabled()) {
            return null;
        }
        if (!passwordHasher.matches(password, user.getPasswordHash())) {
            return null;
        }
        user.setLastLoginAt(Instant.now());
        return userRepository.save(user);
    }

    @Override
    public String issueResetToken(String email) {
        User user = userRepository.findByEmail(email).orElseThrow();
        return tokenService.newResetToken(user.getId());
    }
}
