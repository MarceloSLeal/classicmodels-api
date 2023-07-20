package com.classicmodels.api.mapper;

import com.classicmodels.api.model.CustomerRepModel;
import com.classicmodels.api.model.input.CustomerImput;
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

    public CustomerRepModel toModel(Customer customer) {
        return modelMapper.map(customer, CustomerRepModel.class);
    }

    public List<CustomerRepModel> toCollectionModel(List<Customer> customers) {
        return customers.stream()
                .map(this::toModel)
                .collect(Collectors.toList());
    }

    public Customer toEntity(CustomerImput customerImput) {
        return modelMapper.map(customerImput, Customer.class);
    }

}
