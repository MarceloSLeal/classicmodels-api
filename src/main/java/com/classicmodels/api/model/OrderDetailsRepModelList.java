package com.classicmodels.api.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderDetailsRepModelList {

    private Long orderId;
    private Long productId;
    private Integer quantityOrdered;
    private Double priceEach;
    private Integer orderLineNumber;

}
