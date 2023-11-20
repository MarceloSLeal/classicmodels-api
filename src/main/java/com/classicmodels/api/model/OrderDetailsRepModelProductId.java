package com.classicmodels.api.model;

import com.classicmodels.api.model.lists.OrderDetailsRepModelListProductId;
import com.classicmodels.domain.model.OrderDetails;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Getter
@Setter
public class OrderDetailsRepModelProductId {

    private Long productId;
    private List<OrderDetailsRepModelListProductId> orderList;

    public OrderDetailsRepModelProductId(List<OrderDetails> orderDetails) {
        this.orderList = new ArrayList<>();

        if (orderDetails != null) {
            this.productId = orderDetails.get(0).getProductId();

            for (OrderDetails orderDetail : orderDetails) {
                OrderDetailsRepModelListProductId itemList = new OrderDetailsRepModelListProductId();
                itemList.setOrderId(orderDetail.getOrderId());
                itemList.setQuantityOrdered(orderDetail.getQuantityOrdered());
                itemList.setPriceEach(orderDetail.getPriceEach());
                itemList.setOrderLineNumber(orderDetail.getOrderLineNumber());
                this.orderList.add(itemList);
            }

            this.orderList.sort(Comparator.comparingLong(OrderDetailsRepModelListProductId::getOrderId));
        }
    }

}
