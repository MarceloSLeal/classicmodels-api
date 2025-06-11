package com.classicmodels.api.model.input;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmployeeInput {

    @NotNull
    @Digits(integer = 19, fraction = 0, message = "Invalid Long value")
    private Long officeId;

    @NotBlank
    @Size(max = 50)
    private String lastName;

    @NotBlank
    @Size(max = 50)
    private String firstName;

    @NotBlank
    @Size(max = 10)
    private String extension;

    @Email
    @Size(max = 100)
    private String email;

    @Digits(integer = 19, fraction = 0, message = "Invalid Long value")
    private Long reportsTo;

    @NotBlank
    @Size(max = 50)
    private String jobTitle;

}
