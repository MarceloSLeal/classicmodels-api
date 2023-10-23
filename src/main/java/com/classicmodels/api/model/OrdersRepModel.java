package com.classicmodels.api.model;

import com.classicmodels.domain.model.OrdersStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
public class OrdersRepModel {

    private Long id;
    private OffsetDateTime date;
    private OffsetDateTime requiredDate;
    private OffsetDateTime shippedDate;
    private OrdersStatus status;
    private String comments;
    private Long customerId;

}
