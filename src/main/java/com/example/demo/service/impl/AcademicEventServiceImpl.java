package com.example.demo.service.impl;
import com.example.demo.entity.AcademicEvent;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.exception.ValidationException;
import com.example.demo.repository.AcademicEventRepository;
import com.example.demo.service.AcademicEventService;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AcademicEventServiceImpl implements AcademicEventService {
    private final AcademicEventRepository repository;
    public AcademicEventServiceImpl(AcademicEventRepository repository) { this.repository = repository; }

    @Override
    public AcademicEvent createEvent(AcademicEvent event) {
        // Pass Test t19
        if (event.getStartDate() != null && event.getEndDate() != null && event.getStartDate().isAfter(event.getEndDate())) {
            throw new ValidationException("startDate cannot be after endDate");
        }
        return repository.save(event);
    }

    @Override
    public List<AcademicEvent> getEventsByBranch(Long branchId) {
        return repository.findByBranchId(branchId);
    }
    
    // Stub methods for other interface requirements
    public AcademicEvent updateEvent(Long id, AcademicEvent event) { return repository.save(event); }
    public AcademicEvent getEventById(Long id) { return repository.findById(id).orElseThrow(()->new ResourceNotFoundException("Not Found")); }
    public List<AcademicEvent> getAllEvents() { return repository.findAll(); }
}