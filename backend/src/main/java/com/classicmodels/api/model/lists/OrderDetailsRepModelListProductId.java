package com.classicmodels.api.model.lists;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderDetailsRepModelListProductId {

    private Long orderId;
    private Integer quantityOrdered;
    private Double priceEach;
    private Integer orderLineNumber;

}
