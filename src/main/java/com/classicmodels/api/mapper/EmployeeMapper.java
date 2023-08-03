package com.classicmodels.api.mapper;

import com.classicmodels.api.model.EmployeeRepModel;
import com.classicmodels.api.model.input.EmployeeInput;
import com.classicmodels.domain.model.Employee;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Component
public class EmployeeMapper {

    private ModelMapper modelMapper;

    public  EmployeeRepModel toModel(Employee employee) {
        return new EmployeeRepModel(employee);
//        return modelMapper.map(employee, EmployeeRepModel.class);
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
