package com.classicmodels.domain.model;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
@Setter
@Entity
@Table(name = "customers")
public class Customer {

    @ManyToOne
    @JoinColumn(name = "salesRepEmployeeNumber")
    private Employees employees;

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer customerNumber;

    @NotBlank
    @Size(max = 50)
    private String customerName;

    @NotBlank
    private String contactLastName;

    @NotBlank
    private String contactFirstName;

    @NotBlank
    @Size(max = 50)
    @Pattern(regexp = "[0-9+\\-().\\s]+")
    private String phone;

    @NotBlank
    private String addressLine1;

    @NotBlank
    private String addressLine2;

    @NotBlank
    @Size(max = 50)
    private String city;

    @Size(max = 50)
    private String state;

    @Size(max = 15)
    private String postalCode;

    @NotBlank
    @Size(max = 50)
    private String country;

    @DecimalMin(value = "0.00", inclusive = true)
    @DecimalMax(value = "9999999999.99", inclusive = true)
    private Double creditLimit;

    //TODO create a validation on service to verify if the salesRepEmployeeNumber came from the POST exists on table Employees and
    //TODO and if its null
//    @Positive
//    @Digits(integer = 10, fraction = 0)
//    private Integer salesRepEmployeeNumber;

}
