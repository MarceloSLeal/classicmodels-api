package com.classicmodels.domain.model;

import lombok.Getter;

@Getter
public enum OrdersStatus {

    SHIPPED,
    RESOLVED,
    CANCELLED,
    ON_HOLD,
    DISPUTED,
    IN_PROCESS

}
