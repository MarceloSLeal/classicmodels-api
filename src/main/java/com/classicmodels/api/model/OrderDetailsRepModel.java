package com.classicmodels.api.model;

import com.classicmodels.domain.model.OrderDetails;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
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

            for (int i = 0; i < orderDetails.size(); i++) {
                OrderDetailsRepModelList itemList = new OrderDetailsRepModelList();
                itemList.setProductId(orderDetails.get(i).getProductId());
                itemList.setQuantityOrdered(orderDetails.get(i).getQuantityOrdered());
                itemList.setPriceEach(orderDetails.get(i).getPriceEach());
                itemList.setOrderLineNumber(orderDetails.get(i).getOrderLineNumber());
                this.orderList.add(itemList);
            }
        }
    }
}
