package com.classicmodels.api.model.input;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
public class PaymentsInput {

    @NotNull
    private Long customerId;

    @FutureOrPresent
    private OffsetDateTime paymentDate;

    @Digits(integer = 10, fraction = 2, message = "max 10 digits and 2 after the .")
    private Double amount;

}
