package com.classicmodels.api.controller;

import com.classicmodels.api.mapper.CalendarMapper;
import com.classicmodels.api.model.CalendarRepModel;
import com.classicmodels.api.model.input.CalendarInput;
import com.classicmodels.domain.exception.BusinessException;
import com.classicmodels.domain.exception.EntityNotFoundException;
import com.classicmodels.domain.model.Calendar;
import com.classicmodels.domain.repository.CalendarRepository;
import com.classicmodels.domain.service.CalendarCatalogService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.OffsetDateTime;
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
        Calendar newCalendar = calendarMapper.toEntity(calendarInput);

        CalendarRepModel calendarRepModel = calendarMapper.toModel(newCalendar);

        boolean calendarId = calendarRepository.findById(calendarInput.getId())
                .stream()
                .anyMatch( CalendarExists  -> !CalendarExists.equals(newCalendar));

        if (calendarId) {
            throw new BusinessException("Event already exists");
        }

        calendarCatalogService.save(newCalendar);

        return calendarRepModel;
    }

    @PutMapping("/{id}")
    public ResponseEntity<CalendarRepModel> atualizar(@PathVariable String id, @Valid @RequestBody CalendarInput calendarInput) {

        Calendar calendar = calendarRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Event id dont exists"));

        Calendar calendarEdit = calendarMapper.toEntity(calendarInput);
        calendarEdit.setId(id);

        calendarCatalogService.save(calendarEdit);

        return ResponseEntity.ok(calendarMapper.toModel(calendarEdit));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable String id) {

        if (!calendarRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        calendarCatalogService.delete(id);

        return ResponseEntity.noContent().build();
    }

}
