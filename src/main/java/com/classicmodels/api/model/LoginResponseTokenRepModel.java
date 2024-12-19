package com.classicmodels.api.model;

import com.classicmodels.domain.model.UsersRole;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class LoginResponseTokenRepModel {


    private String token;
    private String user;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime expires;
    private UsersRole role;

    public LoginResponseTokenRepModel(String token, String user, LocalDateTime expires, UsersRole role){
        this.token = token;
        this.user = user;
        this.expires = expires;
        this.role = role;
    }

}
