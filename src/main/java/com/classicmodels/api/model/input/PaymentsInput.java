package com.classicmodels.api.model.input;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
public class PaymentsInput {

    @NotNull
    private Long customerId;

    @NotBlank
    @Size(max = 50)
    private String checkNumber;

    @Pattern(regexp = "[0-9]{4}-[0-9]{2}-[0-9]{2}T[0-9]{2}:[0-9]{2}:[0-9]{2}(\\.[0-9]+)?([Zz]|([+-])([01]\\d|2[0-3]):?([0-5]\\d)?)?")
    private String paymentDate;

    @Digits(integer = 10, fraction = 2, message = "max 10 digits and 2 after the .")
    private Double amount;

}
