package com.classicmodels.api.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponseTokenRepModel {

    public String token;

    public LoginResponseTokenRepModel(String token){
        this.token = token;
    }

}
