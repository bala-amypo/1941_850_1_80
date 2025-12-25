package com.example.demo.service.impl;
import com.example.demo.entity.EventMergeRecord;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.EventMergeRecordRepository;
import com.example.demo.service.EventMergeService;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EventMergeServiceImpl implements EventMergeService {
    private final EventMergeRecordRepository repository;
    public EventMergeServiceImpl(EventMergeRecordRepository repository) { this.repository = repository; }

    @Override
    public EventMergeRecord mergeEvents(List<Long> eventIds, String reason) {
        String ids = eventIds.stream().map(String::valueOf).collect(Collectors.joining(","));
        EventMergeRecord record = new EventMergeRecord();
        record.setSourceEventIds(ids);
        record.setMergeReason(reason);
        record.setMergedTitle("Merged Group");
        record.setMergedStartDate(LocalDate.now());
        record.setMergedEndDate(LocalDate.now());
        return repository.save(record);
    }
    @Override
    public List<EventMergeRecord> getAllMergeRecords() { return repository.findAll(); }
    @Override
    public EventMergeRecord getMergeRecordById(Long id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Merge record not found: " + id));
    }
    @Override
    public List<EventMergeRecord> getMergeRecordsByDate(LocalDate start, LocalDate end) {
        return repository.findByMergedStartDateBetween(start, end);
    }
}