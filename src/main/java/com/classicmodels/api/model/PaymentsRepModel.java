package com.classicmodels.api.model;

import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Setter
public class PaymentsRepModel {

    private long customerId;
    private UUID checkNumber;
    private OffsetDateTime paymentDate;
    private Double amount;

}
