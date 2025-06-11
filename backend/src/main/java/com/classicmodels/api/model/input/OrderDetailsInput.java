package com.classicmodels.api.model.input;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderDetailsInput {

    @NotNull
    private Long orderId;
    @NotNull
    private Long productId;
    @Positive
    @NotNull
    private Integer quantityOrdered;
    @Positive
    @NotNull
    private Double priceEach;
    @Positive
    @NotNull
    private Integer orderLineNumber;

}
