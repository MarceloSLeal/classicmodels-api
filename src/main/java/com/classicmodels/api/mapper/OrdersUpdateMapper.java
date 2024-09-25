package com.classicmodels.api.mapper;

import com.classicmodels.api.model.OrdersRepModel;
import com.classicmodels.api.model.input.OrdersInputUpdate;
import com.classicmodels.domain.model.Orders;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Component
public class OrdersUpdateMapper {

    private ModelMapper modelMapper;

    public OrdersRepModel toModel(Orders orders) {
       return modelMapper.map(orders, OrdersRepModel.class);
    }

    public List<OrdersRepModel> toCollectionModel(List<Orders> orders) {
        return orders.stream()
                .map(this::toModel)
                .collect(Collectors.toList());
    }

    public Orders toEntity(OrdersInputUpdate ordersInputUpdate) {
        return modelMapper.map(ordersInputUpdate, Orders.class);
    }

}