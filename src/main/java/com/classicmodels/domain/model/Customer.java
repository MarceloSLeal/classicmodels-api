package com.classicmodels.domain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
@Setter
@Entity
@Table(name = "customers")
public class Customer {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer customerNumber;

    @NotBlank
    @Size(max = 50)
    private String customerName;

    @NotBlank
    @Size(max = 50)
    private String contactLastName;

    @NotBlank
    @Size(max = 50)
    private String contactFirstName;

    @NotBlank
    @Size(max = 50)
    @Pattern(regexp = "[0-9+\\-().\\s]+")
    private String phone;

    @NotBlank
    @Size(max = 50)
    private String addressLine1;

    @Size(max = 50)
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

    @Pattern(regexp = "[0-9]{10}")
    private Integer salesRepEmployeeNumber;

}
