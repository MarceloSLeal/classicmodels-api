package com.classicmodels.api.model;

import com.classicmodels.domain.model.UsersRole;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class TokenRepModel {

    private String token;
    private String refreshToken;
    private String user;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime expires;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime refreshExpires;
    private UsersRole role;

    public TokenRepModel(String token,
                         String refreshToken,
                         String user,
                         LocalDateTime expires,
                         LocalDateTime refreshExpires,
                         UsersRole role) {
        this.token = token;
        this.refreshToken = refreshToken;
        this.user = user;
        this.expires = LocalDateTime.from(expires);
        this.refreshExpires = LocalDateTime.from(refreshExpires);
        this.role = role;
    }

}
