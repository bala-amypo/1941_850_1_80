package com.example.demo.repository;

import com.example.demo.entity.EventMergeRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;

public interface EventMergeRecordRepository extends JpaRepository<EventMergeRecord, Long> {
    List<EventMergeRecord> findByMergedStartDateBetween(LocalDate start, LocalDate end);
}