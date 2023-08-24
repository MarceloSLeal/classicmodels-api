package com.classicmodels.domain.model;

import java.io.Serializable;
import java.util.Objects;

public class PaymentsId implements Serializable {

    private Long customerId;
    private String checkNumber;

    public PaymentsId() {
    }

    public PaymentsId(Long customerId, String checkNumber) {
        this.customerId = customerId;
        this.checkNumber = checkNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PaymentsId that = (PaymentsId) o;

        if (!Objects.equals(customerId, that.customerId)) return false;
        return Objects.equals(checkNumber, that.checkNumber);
    }

    @Override
    public int hashCode() {
        int result = customerId != null ? customerId.hashCode() : 0;
        result = 31 * result + (checkNumber != null ? checkNumber.hashCode() : 0);
        return result;
    }
}
