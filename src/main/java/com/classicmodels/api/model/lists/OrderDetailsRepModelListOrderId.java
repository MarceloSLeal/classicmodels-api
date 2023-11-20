package com.classicmodels.api.model.lists;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderDetailsRepModelListOrderId {

    private Long productId;
    private Integer quantityOrdered;
    private Double priceEach;
    private Integer orderLineNumber;

}
