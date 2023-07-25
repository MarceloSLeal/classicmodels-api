package com.classicmodels.api.mapper;

import com.classicmodels.api.model.CustomerRepModel;
import com.classicmodels.api.model.input.CustomerInput;
import com.classicmodels.domain.model.Customer;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Component
public class CustomerMapper {

    private ModelMapper modelMapper;

    public CustomerMapper() {

    }

    public CustomerRepModel toModel(Customer customer) {
        return modelMapper.map(customer, CustomerRepModel.class);
    }

    public List<CustomerRepModel> toCollectionModel(List<Customer> customers) {
        return customers.stream()
                .map(this::toModel)
                .collect(Collectors.toList());
    }

    public Customer toEntity(CustomerInput customerInput) {
        return modelMapper.map(customerInput, Customer.class);
    }

    public CustomerInput toInput(Customer customer) {
        return modelMapper.map(customer, CustomerInput.class);
    }
}
