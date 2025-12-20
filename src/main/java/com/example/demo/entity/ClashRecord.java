package com.example.demo.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "clash_records")
public class ClashRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long eventAId;
    private Long eventBId;
    private String clashType; // DATE_OVERLAP, TIME_CONFLICT
    private String severity; // LOW, MEDIUM, HIGH
    private String details;
    private LocalDateTime detectedAt;
    private Boolean resolved;

    @PrePersist
    protected void onCreate() {
        this.detectedAt = LocalDateTime.now();
        if (this.resolved == null) {
            this.resolved = false;
        }
    }

    // Constructors
    public ClashRecord() {}
    public ClashRecord(Long id, Long eventAId, Long eventBId, String clashType, String severity, String details, LocalDateTime detectedAt, Boolean resolved) {
        this.id = id;
        this.eventAId = eventAId;
        this.eventBId = eventBId;
        this.clashType = clashType;
        this.severity = severity;
        this.details = details;
        this.detectedAt = detectedAt;
        this.resolved = resolved;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getEventAId() { return eventAId; }
    public void setEventAId(Long eventAId) { this.eventAId = eventAId; }
    public Long getEventBId() { return eventBId; }
    public void setEventBId(Long eventBId) { this.eventBId = eventBId; }
    public String getClashType() { return clashType; }
    public void setClashType(String clashType) { this.clashType = clashType; }
    public String getSeverity() { return severity; }
    public void setSeverity(String severity) { this.severity = severity; }
    public String getDetails() { return details; }
    public void setDetails(String details) { this.details = details; }
    public LocalDateTime getDetectedAt() { return detectedAt; }
    public Boolean getResolved() { return resolved; }
    public void setResolved(Boolean resolved) { this.resolved = resolved; }
}