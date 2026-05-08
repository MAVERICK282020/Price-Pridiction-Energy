package com.example.energy.controller;

import com.example.energy.dto.LoginRequest;
import com.example.energy.model.AppUser;
import com.example.energy.service.UserService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public AppUser register(@Valid @RequestBody AppUser user) {
        return userService.register(user);
    }

    @PostMapping("/login")
    public Map<String, String> login(@Valid @RequestBody LoginRequest request) {
        return Map.of("message", userService.login(request));
    }
}
