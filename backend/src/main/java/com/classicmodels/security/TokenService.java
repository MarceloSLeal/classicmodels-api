package com.classicmodels.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.classicmodels.api.model.TokenRepModel;
import com.classicmodels.domain.model.Users;
import com.classicmodels.domain.repository.UsersRepository;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Slf4j
@Setter
@Service
public class TokenService {

    @Value("${JWT_TOKEN_KEY}")
    private String tokenKey;

    ZoneOffset zoneOffset = ZoneOffset.of("-03:00");

    @Autowired
    private UsersRepository usersRepository;

    public TokenRepModel generateToken(Users users){
        try{
            Algorithm algorithm = Algorithm.HMAC256(tokenKey);

            return new TokenRepModel(
                    JWT.create()
                            .withIssuer("auth-api")
                            .withSubject(users.getEmail())
                            .withExpiresAt(tokenExpiration())
                            .sign(algorithm),
                    JWT.create()
                            .withIssuer("auth-api")
                            .withSubject(users.getEmail())
                            .withExpiresAt(refreshTokenExpiration())
                            .sign(algorithm),
                    users.getEmail(),
                    LocalDateTime.ofInstant(tokenExpiration(), zoneOffset),
                    LocalDateTime.ofInstant(refreshTokenExpiration(), zoneOffset),
                    users.getRole()
            );

        } catch (JWTCreationException exception){
            throw new RuntimeException("Error while generating token", exception);
        }
    }

    public TokenRepModel refreshToken(String token){
        try{
            DecodedJWT decodedJWT = JWT.decode(token);
            String userName = decodedJWT.getSubject();

            log.debug("Decoded username from token: {}", userName);
            Users user = (Users) usersRepository.findByEmail(userName);

            return generateToken(user);

        } catch (Exception e) {
            throw new RuntimeException("Error while refreshing token", e);
        }
    }

    public String validateToken(String token){
        try {
            Algorithm algorithm = Algorithm.HMAC256(tokenKey);
            return JWT.require(algorithm)
                    .withIssuer("auth-api")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException exception){
            return "";
        }
    }

    private Instant tokenExpiration(){
        return LocalDateTime.now().plusMinutes(10).toInstant(zoneOffset);
    }

    private Instant refreshTokenExpiration() {
        return LocalDateTime.now().plusMinutes(30).toInstant(zoneOffset);
    }

}
