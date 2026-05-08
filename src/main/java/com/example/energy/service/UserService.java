package com.example.energy.service;

import com.example.energy.dto.LoginRequest;
import com.example.energy.model.AppUser;
import com.example.energy.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public AppUser register(AppUser user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException("Email already registered.");
        }
        return userRepository.save(user);
    }

    public String login(LoginRequest request) {
        return userRepository.findByEmail(request.getEmail())
                .filter(user -> user.getPassword().equals(request.getPassword()))
                .map(user -> "Login successful. Welcome " + user.getFullName() + "!")
                .orElse("Invalid email or password.");
    }
}
