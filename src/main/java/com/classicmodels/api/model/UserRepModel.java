package com.classicmodels.api.model;

import com.classicmodels.domain.model.UsersRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserRepModel {

    private String email;
    private UsersRole role;

}
