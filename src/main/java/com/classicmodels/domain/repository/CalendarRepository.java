package com.classicmodels.domain.repository;

import com.classicmodels.domain.model.Calendar;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CalendarRepository extends JpaRepository<Calendar, String> {
}
