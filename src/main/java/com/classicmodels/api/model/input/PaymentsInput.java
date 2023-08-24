package com.classicmodels.api.model.input;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class PaymentsInput {

    @NotBlank
    private Long customerId;

    @NotBlank
    @Size(max = 50)
    private String checkNumber;

    @NotBlank
    @PastOrPresent
    private Date paymentDate;

    @DecimalMin(value = "0.00")
    @DecimalMax(value = "9999999999.99")
    private Double amount;

}
