package com.classicmodels.api.model.input;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
public class PaymentsInput {

    @NotNull
    private Long orderId;

    @FutureOrPresent
    @JsonProperty("paymentDate")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    private OffsetDateTime paymentDate;

    @Digits(integer = 10, fraction = 2, message = "max 10 digits and 2 after the .")
    private Double amount;

}
