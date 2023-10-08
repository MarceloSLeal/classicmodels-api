package com.classicmodels.api.model.input;

import jakarta.annotation.Nullable;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
public class OrdersInput {

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
