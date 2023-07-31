package com.classicmodels.api.mapper;

import com.classicmodels.api.model.EmployeeRepModel;
import com.classicmodels.api.model.input.EmployeeInput;
import com.classicmodels.domain.model.Customer;
import com.classicmodels.domain.model.Employee;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Component
public class EmployeeMapper {

//    private ModelMapper modelMapper;

    private static final ModelMapper modelMapper = new ModelMapper();

//    public  EmployeeRepModel toModel(Employee employee) {
//        return new EmployeeRepModel(employee);
//    }
//
//    public List<EmployeeRepModel> toCollectionModel(List<Employee> employees) {
//        return employees.stream()
//                .map(this::toModel)
//                .collect(Collectors.toList());
//    }
//
//    public Employee toEntity(EmployeeInput employeeInput) {
//        return modelMapper.map(employeeInput, Employee.class);
//    }



//    public EmployeeRepModel toModel(Employee employee) {
//        EmployeeRepModel employeeRepModel = modelMapper.map(employee, EmployeeRepModel.class);
//        employeeRepModel.setCustomersId(getCustomerIds(employee.getCustomers()));
//        return employeeRepModel;
//    }
//
//    public List<EmployeeRepModel> toCollectionModel(List<Employee> employees) {
//        return employees.stream()
//                .map(this::toModel)
//                .collect(Collectors.toList());
//    }
//
//    public Employee toEntity(EmployeeInput employeeInput) {
//        return modelMapper.map(employeeInput, Employee.class);
//    }
//
//    private List<Long> getCustomerIds(List<Customer> customers) {
//        return customers.stream()
//                .map(Customer::getId)
//                .collect(Collectors.toList());
//    }

    public static List<EmployeeRepModel> toCollectionModel(List<Employee> employees) {
        List<EmployeeRepModel> employeeRepModels = new ArrayList<>();
        for (Employee employee : employees) {
            EmployeeRepModel employeeRepModel = modelMapper.map(employee, EmployeeRepModel.class);
            List<Long> customersId = employee.getCustomers().stream()
                    .map(Customer::getId)
                    .collect(Collectors.toList());
            employeeRepModel.setCustomersId(customersId);
            employeeRepModels.add(employeeRepModel);
        }
        return employeeRepModels;
    }


}
