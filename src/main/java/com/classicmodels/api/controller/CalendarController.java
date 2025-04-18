package com.classicmodels.api.controller;

import com.classicmodels.api.mapper.CalendarMapper;
import com.classicmodels.api.model.CalendarRepModel;
import com.classicmodels.api.model.input.CalendarInput;
import com.classicmodels.domain.exception.BusinessException;
import com.classicmodels.domain.model.Calendar;
import com.classicmodels.domain.repository.CalendarRepository;
import com.classicmodels.domain.service.CalendarCatalogService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/calendar")
public class CalendarController {

    private CalendarRepository calendarRepository;
    private CalendarCatalogService calendarCatalogService;
    private CalendarMapper calendarMapper;

    @GetMapping
    public List<CalendarRepModel> listar() {

        return calendarMapper.toCollectionModel(calendarRepository.findAll());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CalendarRepModel adicionar(@Valid @RequestBody CalendarInput calendarInput) {

        System.out.println("CalendarInput: " + calendarInput.getStart());

        Calendar newCalendar = calendarMapper.toEntity(calendarInput);

        CalendarRepModel calendarRepModel = calendarMapper.toModel(newCalendar);

        boolean calendarId = calendarRepository.findById(calendarInput.getId())
                .stream()
                .anyMatch( CalendarExists  -> CalendarExists.equals(newCalendar));

        if (calendarId) {
            throw new BusinessException("Calendar already exists");
        }

        calendarCatalogService.save(newCalendar);

        return calendarRepModel;
    }

}
