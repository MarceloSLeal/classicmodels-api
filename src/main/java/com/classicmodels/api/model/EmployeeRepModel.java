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

    private Long officeId;
    //private List<Long> customersId;
    //private  Long customersId;
    //private String customersId;
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

        //this.officeId = employee.getOfficeId();
        this.officeId = employee.getOfficeId().getId();

        //if (employee.getCustomersId() != null) {
        //    this.customersId = Arrays.stream(employee.getCustomersId().split(","))
        //            .map(Long::parseLong)
        //            .collect(Collectors.toList());
        //} else {
        //    this.customersId = new ArrayList<>();
        //}

        //this.customersId = employee.getCustomersId();
        this.customersId = Arrays.stream(employee.getCustomersId().split(","))
                .map(Long::parseLong)
                .collect(Collectors.toList());
        this.id = employee.getId();
        this.lastName = employee.getLastName();
        this.firstName = employee.getFirstName();
        this.extension = employee.getExtension();
        this.email = employee.getEmail();
        this.reportsTo = (employee.getReportsTo() != null) ? employee.getReportsTo() : null;
        this.jobTitle = employee.getJobTitle();
    }
}
