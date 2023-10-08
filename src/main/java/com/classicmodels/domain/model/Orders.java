package com.classicmodels.domain.model;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.Date;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
@Setter
@Entity
@Table(name = "orders")
public class Orders {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @FutureOrPresent
    @NotNull
    private OffsetDateTime date;

    @FutureOrPresent
    @NotNull
    private OffsetDateTime requiredDate;

    @FutureOrPresent
    @Nullable
    private OffsetDateTime shippedDate;

    @Size(max = 15)
    @NotBlank
    private String status;

    @Nullable
    private String comments;

    @NotBlank
    @Digits(integer = 10, fraction = 0, message = "Invalid integer value")
    private Integer customerId;

}
