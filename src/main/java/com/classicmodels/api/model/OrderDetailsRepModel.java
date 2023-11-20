package com.classicmodels.api.model;

import com.classicmodels.domain.model.OrderDetails;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Getter
@Setter
public class OrderDetailsRepModel {

    private Long orderId;
    private List<OrderDetailsRepModelList> orderList;

    public OrderDetailsRepModel(List<OrderDetails> orderDetails) {
        this.orderList = new ArrayList<>();

        if (orderDetails != null) {
            this.orderId = orderDetails.get(0).getOrderId();

            for (OrderDetails orderDetail : orderDetails) {
                OrderDetailsRepModelList itemList = new OrderDetailsRepModelList();
                itemList.setProductId(orderDetail.getProductId());
                itemList.setQuantityOrdered(orderDetail.getQuantityOrdered());
                itemList.setPriceEach(orderDetail.getPriceEach());
                itemList.setOrderLineNumber(orderDetail.getOrderLineNumber());
                this.orderList.add(itemList);
            }

            this.orderList.sort(Comparator.comparingInt(OrderDetailsRepModelList::getOrderLineNumber));
        }
    }
}
