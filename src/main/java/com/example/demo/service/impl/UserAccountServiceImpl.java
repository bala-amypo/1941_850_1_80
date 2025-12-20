package com.example.demo.service.impl;

import com.example.demo.entity.UserAccount;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.exception.ValidationException;
import com.example.demo.repository.UserAccountRepository;
import com.example.demo.service.UserAccountService;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserAccountServiceImpl implements UserAccountService {

    private final UserAccountRepository userAccountRepository;

    // MANDATORY: Constructor Injection (Removed PasswordEncoder for Review 1)
    public UserAccountServiceImpl(UserAccountRepository userAccountRepository) {
        this.userAccountRepository = userAccountRepository;
    }

    @Override
    public UserAccount register(UserAccount user) {
        // Validation: Check if email already exists
        if (userAccountRepository.existsByEmail(user.getEmail())) {
            throw new ValidationException("Email already in use");
        }
        
        // Default role assignment
        if (user.getRole() == null || user.getRole().isEmpty()) {
            user.setRole("REVIEWER");
        }

        // NOTE: For Review 1, we save the password directly (plain text).
        // For final review, you must bring back BCrypt encoding here.
        user.setPassword(user.getPassword());

        return userAccountRepository.save(user);
    }

    @Override
    public UserAccount findByEmail(String email) {
        return userAccountRepository.findByEmail(email)
            .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + email));
    }

    @Override
    public UserAccount getUser(Long id) {
        return userAccountRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + id));
    }

    @Override
    public List<UserAccount> getAllUsers() {
        return userAccountRepository.findAll();
    }
}