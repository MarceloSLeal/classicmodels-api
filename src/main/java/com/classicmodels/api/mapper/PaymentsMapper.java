package com.classicmodels.api.mapper;

import com.classicmodels.api.model.PaymentsRepModel;
import com.classicmodels.api.model.input.PaymentsInput;
import com.classicmodels.domain.model.Payments;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Component
public class PaymentsMapper {

    private ModelMapper modelMapper;

    public PaymentsRepModel toModel(Payments payments) {
        return modelMapper.map(payments, PaymentsRepModel.class);
    }

    public List<PaymentsRepModel> toCollectionModel(List<Payments> payments) {
        return payments.stream()
                .map(this::toModel)
                .collect(Collectors.toList());
    }

    public Payments toEntity(PaymentsInput paymentsInput) {
        return modelMapper.map(paymentsInput, Payments.class);
    }

}
