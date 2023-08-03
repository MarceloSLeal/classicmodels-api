package com.classicmodels.api.model;

import com.classicmodels.domain.model.Customer;
import com.classicmodels.domain.model.Employee;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class EmployeeRepModel {

    private Long officesId;
    private List<Long> customersId;
    private Long id;
    private String lastName;
    private String firstName;
    private String extension;
    private String email;
    private Long reportsTo;
    private String jobTitle;

    public EmployeeRepModel() {
    }

    public EmployeeRepModel(Employee employee) {

        this.officesId = employee.getOfficeId();

        if (employee.getCustomersId() != null) {
            this.customersId = Arrays.stream(employee.getCustomersId().split(","))
                    .map(Long::parseLong)
                    .collect(Collectors.toList());
        } else {
            this.customersId = new ArrayList<>();
        }

        this.id = employee.getId();
        this.lastName = employee.getLastName();
        this.firstName = employee.getFirstName();
        this.extension = employee.getExtension();
        this.email = employee.getEmail();
        this.reportsTo = (employee.getReportsTo() != null) ? employee.getReportsTo() : null;
        this.jobTitle = employee.getJobTitle();
    }
}
