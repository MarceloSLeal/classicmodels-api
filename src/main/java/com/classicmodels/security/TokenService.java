package com.classicmodels.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.classicmodels.api.model.LoginResponseTokenRepModel;
import com.classicmodels.domain.model.Users;
import com.classicmodels.domain.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cglib.core.Local;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${JWT_TOKEN_KEY")
    private String tokenKey;
    private UsersRepository usersRepository;

    public String generateToken(Users users){
        try{
            Algorithm algorithm = Algorithm.HMAC256(tokenKey);
            return JWT.create()
                    .withIssuer("auth-api")
                    .withSubject(users.getLogin())
                    .withExpiresAt(genExpirationDate())
                    .sign(algorithm);
        } catch (JWTCreationException exception){
            throw new RuntimeException("Error while generating token", exception);
        }
    }

    public LoginResponseTokenRepModel refreshToken(String token){
        try{
            Algorithm algorithm = Algorithm.HMAC256(tokenKey);
            JWTVerifier verifier = JWT.require(algorithm).withIssuer("auth-api").build();
            DecodedJWT decodedJWT = verifier.verify(token);

            String userName = decodedJWT.getSubject();
            UserDetails user = usersRepository.findByLogin(userName);

            String newtoken = generateToken((Users) user);

            LoginResponseTokenRepModel repModel = new LoginResponseTokenRepModel(
                    newtoken,
                    ((Users) user).getLogin(),
                    LocalDateTime.now().plusMinutes(2).atOffset(ZoneOffset.of("-03:00")).toLocalDateTime());

            return repModel;
        } catch (JWTCreationException exception) {
            throw new RuntimeException("Error while refreshing token", exception);
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

    private Instant genExpirationDate(){
        return LocalDateTime.now().plusMinutes(2).toInstant(ZoneOffset.of("-03:00"));
    }

}
