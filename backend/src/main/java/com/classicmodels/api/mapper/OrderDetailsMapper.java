package com.classicmodels.api.mapper;

import com.classicmodels.api.model.OrderDetailsRepModel;
import com.classicmodels.api.model.input.OrderDetailsInput;
import com.classicmodels.domain.model.OrderDetails;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Component
public class OrderDetailsMapper {

    private ModelMapper modelMapper;

    public OrderDetailsRepModel toModel(OrderDetails orderDetails) {
        return modelMapper.map(orderDetails, OrderDetailsRepModel.class);
    }

    public List<OrderDetails> toCollecionModel(List<OrderDetailsInput> orderDetailsInputs) {
        return orderDetailsInputs.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }

    public List<OrderDetailsRepModel> toCollectionEntity(List<OrderDetails> orderDetails) {
        return orderDetails.stream()
                .map(this::toModel)
                .collect(Collectors.toList());
    }

    public OrderDetails toEntity(OrderDetailsInput orderDetailsInput) {
        return modelMapper.map(orderDetailsInput, OrderDetails.class);
    }

}
