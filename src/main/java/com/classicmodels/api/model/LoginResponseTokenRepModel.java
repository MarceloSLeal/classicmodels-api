package com.classicmodels.api.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDateTime;

@Getter
@Setter
public class LoginResponseTokenRepModel {


    private String token;
    private String user;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime expires;

    public LoginResponseTokenRepModel(String token, String user, LocalDateTime expires){
        this.token = token;
        this.user = user;
        this.expires = expires;
    }

}
