package com.classicmodels.domain.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Entity(name = "calendar")
@Table(name = "calendar")
@Getter
@Setter
public class Calendar {

    @NotBlank
    @Size(max = 36)
    @Id
    private String id;

    @NotBlank
    @Size(max = 50)
    private String title;

    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mmXXX")
    private OffsetDateTime start;

    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mmXXX")
    private OffsetDateTime end;

    @Column(columnDefinition = "TINYINT(1)")
    private boolean allday;

}
