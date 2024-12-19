package com.classicmodels.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public enum UsersRole {

    ADMIN,
    USER;

    private String role;

    void UserRole(String role) {
        this.role = role;
    }

}
