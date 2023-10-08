package com.classicmodels.api.model;

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
    private String status;
    private String comments;
    private Integer customerId;

}
