package com.example.demo.service.impl;
import com.example.demo.entity.BranchProfile;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.exception.ValidationException;
import com.example.demo.repository.BranchProfileRepository;
import com.example.demo.service.BranchProfileService;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class BranchProfileServiceImpl implements BranchProfileService {
    private final BranchProfileRepository repository;
    public BranchProfileServiceImpl(BranchProfileRepository repository) { this.repository = repository; }

    @Override
    public BranchProfile createBranch(BranchProfile branch) {
        if (repository.findByBranchCode(branch.getBranchCode()).isPresent())
            throw new ValidationException("Branch code exists.");
        return repository.save(branch);
    }
    @Override
    public BranchProfile updateBranchStatus(Long id, boolean active) {
        BranchProfile branch = getBranchById(id);
        branch.setActive(active);
        return repository.save(branch);
    }
    @Override
    public List<BranchProfile> getAllBranches() { return repository.findAll(); }
    @Override
    public BranchProfile getBranchById(Long id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Branch not found: " + id));
    }
    @Override
    public BranchProfile findByBranchCode(String branchCode) {
        return repository.findByBranchCode(branchCode).orElseThrow(() -> new ResourceNotFoundException("Branch not found: " + branchCode));
    }
}