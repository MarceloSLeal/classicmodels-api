package com.classicmodels.api.model.input;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OfficesInput {

    @NotNull
    @Size(max = 50)
    private String city;

    @NotNull
    @Size(max = 50)
    private String phone;

    @NotNull
    @Size(max = 50)
    private String addressLine1;

    @Size(max = 50)
    private String addressLine2;

    @Size(max = 50)
    private String state;

    @NotNull
    @Size(max = 50)
    private String country;

    @NotNull
    @Size(max = 15)
    private String postalCode;

    @NotNull
    @Size(max = 10)
    private String territory;

}
