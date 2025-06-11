package com.classicmodels.api.model.input;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerInput {

    @Email
    @Size(max = 50)
    private String email;

    @NotBlank
    private String name;

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

    @Size(max = 50)
    private String postalCode;

    @NotBlank
    @Size(max = 50)
    private String country;

    @DecimalMin(value = "0.00")
    @DecimalMax(value = "9999999999.99")
    private Double creditLimit;

    private Integer employeeId;

}
