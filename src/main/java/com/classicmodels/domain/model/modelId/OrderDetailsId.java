package com.classicmodels.domain.model.modelId;

import java.io.Serializable;

public class OrderDetailsId implements Serializable {

    private Long orderId;
    private Long productId;

    public OrderDetailsId() {
    }

    public OrderDetailsId(Long orderId, Long productId) {
        this.orderId = orderId;
        this.productId = productId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrderDetailsId that = (OrderDetailsId) o;

        if (!orderId.equals(that.orderId)) return false;
        return productId.equals(that.productId);
    }

    @Override
    public int hashCode() {
        int result = orderId.hashCode();
        result = 31 * result + productId.hashCode();
        return result;
    }
}
