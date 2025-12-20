package com.example.demo.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "branch_profiles")
public class BranchProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String branchCode;

    private String branchName;
    private String contactEmail;
    private LocalDateTime lastSyncAt;
    private Boolean active;

    @PrePersist
    protected void onCreate() {
        this.lastSyncAt = LocalDateTime.now();
        if (this.active == null) this.active = true;
    }

    // Getters and Setters (Omitted for brevity, but you must generate them)
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getBranchCode() { return branchCode; }
    public void setBranchCode(String branchCode) { this.branchCode = branchCode; }
    public String getBranchName() { return branchName; }
    public void setBranchName(String branchName) { this.branchName = branchName; }
    public String getContactEmail() { return contactEmail; }
    public void setContactEmail(String contactEmail) { this.contactEmail = contactEmail; }
    public Boolean getActive() { return active; }
    public void setActive(Boolean active) { this.active = active; }
}