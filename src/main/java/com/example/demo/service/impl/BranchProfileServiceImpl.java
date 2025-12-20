package com.example.demo.service.impl;

import com.example.demo.entity.BranchProfile;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.exception.ValidationException;
import com.example.demo.repository.BranchProfileRepository;
import com.example.demo.service.BranchProfileService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BranchProfileServiceImpl implements BranchProfileService {

    private final BranchProfileRepository branchProfileRepository;

    // MANDATORY: Constructor Injection 
    public BranchProfileServiceImpl(BranchProfileRepository branchProfileRepository) {
        this.branchProfileRepository = branchProfileRepository;
    }

    @Override
    public BranchProfile createBranch(BranchProfile branch) {
        // Validation: Check if branch code already exists [cite: 56, 167]
        Optional<BranchProfile> existing = branchProfileRepository.findByBranchCode(branch.getBranchCode());
        if (existing.isPresent()) {
            throw new ValidationException("Branch code '" + branch.getBranchCode() + "' already exists.");
        }
        // Logic: Save via repository [cite: 207]
        return branchProfileRepository.save(branch);
    }

    @Override
    public BranchProfile updateBranchStatus(Long id, boolean active) {
        // Logic: Fetch branch by id; if not found, throw ResourceNotFoundException [cite: 209]
        BranchProfile branch = branchProfileRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Branch not found with ID: " + id));
        
        // Logic: Set active status and save [cite: 210, 211]
        branch.setActive(active);
        return branchProfileRepository.save(branch);
    }

    @Override
    public List<BranchProfile> getAllBranches() {
        // Logic: Return all branches [cite: 212]
        return branchProfileRepository.findAll();
    }

    @Override
    public BranchProfile getBranchById(Long id) {
        // Logic: If not found, throw ResourceNotFoundException [cite: 214]
        return branchProfileRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Branch not found with ID: " + id));
    }

    @Override
    public BranchProfile findByBranchCode(String branchCode) {
        // Logic: Use findByBranchCode; if empty, throw ResourceNotFoundException [cite: 216, 217]
        return branchProfileRepository.findByBranchCode(branchCode)
                .orElseThrow(() -> new ResourceNotFoundException("Branch not found with code: " + branchCode));
    }
}