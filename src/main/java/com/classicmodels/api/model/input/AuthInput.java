package com.classicmodels.api.model.input;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthInput {

    @Size(max = 50)
    @NotBlank
    private String login;

    @Size(max = 50)
    @NotBlank
    private String password;

}
