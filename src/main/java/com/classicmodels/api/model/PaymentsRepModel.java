package com.classicmodels.api.model;

import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
public class PaymentsRepModel {

    private long customerId;
    private String checkNumber;
    private OffsetDateTime paymentDate;
    private Double amount;

}
