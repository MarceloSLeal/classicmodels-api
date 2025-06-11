package com.classicmodels.api.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerRepModel {

    private Long id;
    private String email;
    private String name;
    private String contactLastName;
    private String contactFirstName;
    private String phone;
    private String addressLine1;
    private String addressLine2;
    private String city;
    private String state;
    private String postalCode;
    private String country;
    private Double creditLimit;
    private Long employeeId;

}
