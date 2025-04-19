package com.classicmodels.api.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
public class CalendarRepModel {

    private String id;
    private String title;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    private OffsetDateTime start;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    private OffsetDateTime end;
    private boolean allday;

    @Override
    public String toString() {
        return "CalendarRepModel{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", start=" + start +
                ", end=" + end +
                ", allday=" + allday +
                '}';
    }
}
