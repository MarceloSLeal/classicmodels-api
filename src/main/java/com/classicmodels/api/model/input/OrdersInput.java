package com.classicmodels.api.model.input;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
public class OrdersInput {

    @FutureOrPresent
    @JsonProperty("requiredDate")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    private OffsetDateTime requiredDate;

    @Nullable
    private String comments;

    @Digits(integer = 10, fraction = 0, message = "Invalid integer value")
    private Long customerId;

}
