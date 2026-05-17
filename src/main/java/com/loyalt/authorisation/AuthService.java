package com.loyalt.authorisation;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Service
public class AuthService {

    private final UserRepository userRepository;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public AuthResponse register(AuthRequest request) {
        if (userRepository.existsByLogin(request.login())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Пользователь с таким логином уже существует");
        }

        User user = new User();
        user.setLogin(request.login());

        user.setPassword(request.password());

        String generatedPartnerId = UUID.randomUUID().toString();
        user.setPartnerId(generatedPartnerId);

        userRepository.save(user);

        return new AuthResponse(user.getPartnerId());
    }

    public AuthResponse login(AuthRequest request) {
        User user = userRepository.findByLogin(request.login())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Неверный логин или пароль"));

        if (!user.getPassword().equals(request.password())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Неверный логин или пароль");
        }

        return new AuthResponse(user.getPartnerId());
    }
}