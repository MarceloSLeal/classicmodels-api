package com.classicmodels.api.model.input;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
public class CalendarInput {

    @NotBlank
    @Size(max = 36)
    private String id;

    @NotBlank
    @Size(max = 50)
    private String title;

    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    private OffsetDateTime start;

    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    private OffsetDateTime end;

    private boolean allday;

    @Override
    public String toString() {
        return "CalendarInput{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", start='" + start + '\'' +
                ", end='" + end + '\'' +
                ", allday=" + allday +
                '}';
    }
}
