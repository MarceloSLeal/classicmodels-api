package com.classicmodels.domain.service;

import com.classicmodels.domain.exception.EntityNotFoundException;
import com.classicmodels.domain.model.Calendar;
import com.classicmodels.domain.repository.CalendarRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Service
public class CalendarCatalogService {

    private CalendarRepository calendarRepository;

    @Transactional
    public void save(Calendar calendar) {
        calendarRepository.save(calendar);
    }

    @Transactional
    public void delete(String id) {
        calendarRepository.deleteById(id);
    }

    public Calendar findById(String id) {
        return calendarRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Calendar not found"));
    }

}
