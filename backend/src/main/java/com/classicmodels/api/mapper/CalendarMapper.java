package com.classicmodels.api.mapper;

import com.classicmodels.api.model.CalendarRepModel;
import com.classicmodels.api.model.input.CalendarInput;
import com.classicmodels.domain.model.Calendar;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Component
public class CalendarMapper {

    private ModelMapper modelMapper;

    public CalendarRepModel toModel(Calendar calendar) {
        return modelMapper.map(calendar, CalendarRepModel.class);
    }

    public List<CalendarRepModel> toCollectionModel(List<Calendar> calendars) {
        return calendars.stream()
                .map(this::toModel)
                .collect(Collectors.toList());
    }

    public Calendar toEntity(CalendarInput calendarInput) {
        return modelMapper.map(calendarInput, Calendar.class);
    }

}
