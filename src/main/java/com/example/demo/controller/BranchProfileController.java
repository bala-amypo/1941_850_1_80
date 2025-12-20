package com.example.demo.controller;

import com.example.demo.entity.BranchProfile;
import com.example.demo.service.BranchProfileService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/branches")
@Tag(name = "Branch Profiles", description = "Manage university branches")
public class BranchProfileController {

    private final BranchProfileService service;

    public BranchProfileController(BranchProfileService service) {
        this.service = service;
    }

    @PostMapping("/")
    public BranchProfile createBranch(@RequestBody BranchProfile branch) {
        return service.createBranch(branch);
    }

    @GetMapping("/")
    public List<BranchProfile> getAllBranches() {
        return service.getAllBranches();
    }

    @GetMapping("/{id}")
    public BranchProfile getBranchById(@PathVariable Long id) {
        return service.getBranchById(id);
    }
}