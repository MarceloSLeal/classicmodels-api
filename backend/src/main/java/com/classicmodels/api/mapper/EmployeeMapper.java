package com.classicmodels.api.mapper;

import com.classicmodels.api.model.EmployeeRepModel;
import com.classicmodels.api.model.input.EmployeeInput;
import com.classicmodels.domain.model.Employee;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class EmployeeMapper {

    final ModelMapper modelMapper;

    @Autowired
    public EmployeeMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;

        Converter<String, List<Long>> customersIdConverter = context -> {
            if (context.getSource() == null || context.getSource().isEmpty()) {
                return new ArrayList<>();
            }
            return Arrays.stream(context.getSource().split(","))
                    .map(Long::parseLong)
                    .collect(Collectors.toList());
        };

        modelMapper.typeMap(Employee.class, EmployeeRepModel.class).addMappings(mapper ->
                mapper.using(customersIdConverter).map(Employee::getCustomersId, EmployeeRepModel::setCustomersId));
    }

    public  EmployeeRepModel toModel(Employee employee) {
        //return new EmployeeRepModel(employee);
        return modelMapper.map(employee, EmployeeRepModel.class);
    }

    public List<EmployeeRepModel> toCollectionModel(List<Employee> employees) {
        return employees.stream()
                .map(this::toModel)
                .collect(Collectors.toList());
    }

    public Employee toEntity(EmployeeInput employeeInput) {
        return modelMapper.map(employeeInput, Employee.class);
    }

}
