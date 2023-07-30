package com.classicmodels.api.model;

import com.classicmodels.domain.model.Customer;
import com.classicmodels.domain.model.Offices;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class EmployeeRepModel {

    private Offices offices;
    private List<Customer> customers = new ArrayList<>();
    private Long id;
    private String lastName;
    private String firstName;
    private String extension;
    private String email;
    private Long reportsTo;
    private String jobTitle;

}
