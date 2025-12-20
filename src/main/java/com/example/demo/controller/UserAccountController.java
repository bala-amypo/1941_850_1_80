package com.example.demo.controller;

import com.example.demo.dto.LoginRequest;
import com.example.demo.dto.RegisterRequest;
import com.example.demo.entity.UserAccount;
import com.example.demo.service.UserAccountService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth")
@Tag(name = "Authentication")
public class UserAccountController {

    private final UserAccountService userAccountService;

    // Review 1: Constructor Injection (No JwtUtil or PasswordEncoder)
    public UserAccountController(UserAccountService userAccountService) {
        this.userAccountService = userAccountService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        // Map DTO to Entity
        UserAccount user = new UserAccount();
        user.setFullName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword()); // Storing plain text for Review 1
        user.setRole(request.getRole());
        user.setDepartment(request.getDepartment());
        
        return ResponseEntity.ok(userAccountService.register(user));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        // Review 1: Logic without Security/JWT
        UserAccount user = userAccountService.findByEmail(request.getEmail());

        // Simple plain text password comparison
        if (!user.getPassword().equals(request.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        // Return simple success message instead of JWT token
        return ResponseEntity.ok("Login Successful (Review 1 Mode: No Token)");
    }

    // Note: These endpoints are normally protected, but open for Review 1 testing
    @GetMapping("/users")
    public List<UserAccount> getAllUsers() {
        return userAccountService.getAllUsers();
    }

    @GetMapping("/users/{id}")
    public UserAccount getUser(@PathVariable Long id) {
        return userAccountService.getUser(id);
    }
}