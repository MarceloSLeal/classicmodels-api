package com.classicmodels.api.model.input;

import com.classicmodels.domain.model.UsersRole;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsersInput {

    @Size(max = 50)
    @NotBlank
    private String login;

    @Size(max = 50)
    @NotBlank
    private String password;

    @Enumerated(EnumType.STRING)
    private UsersRole role;

}
