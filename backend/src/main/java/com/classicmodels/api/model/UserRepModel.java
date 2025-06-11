package com.classicmodels.api.model;

import com.classicmodels.domain.model.UsersRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserRepModel {

    public UserRepModel() {

    }

    public UserRepModel(String email, UsersRole role) {
        this.email = email;
        this.role = role;
    }

    private String id;
    private String email;
    private UsersRole role;

}
