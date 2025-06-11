package com.classicmodels.domain.model;

import com.classicmodels.domain.model.modelId.OrderDetailsId;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
@Setter
@Entity
@IdClass(OrderDetailsId.class)
@Table(name = "orderdetails")
public class OrderDetails {

    @Id
    @NotNull
    private Long orderId;
    @Id
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
