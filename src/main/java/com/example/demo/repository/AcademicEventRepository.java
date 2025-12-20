package com.example.demo.repository;

import com.example.demo.entity.AcademicEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.time.LocalDate;

public interface AcademicEventRepository extends JpaRepository<AcademicEvent, Long> {
    List<AcademicEvent> findByBranchId(Long branchId);
    List<AcademicEvent> findByEventType(String eventType);
    List<AcademicEvent> findByStartDateBetween(LocalDate start, LocalDate end);
}