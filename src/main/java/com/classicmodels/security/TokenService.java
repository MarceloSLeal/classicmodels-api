package com.classicmodels.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.classicmodels.api.model.LoginResponseTokenRepModel;
import com.classicmodels.domain.model.Users;
import com.classicmodels.domain.repository.UsersRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Slf4j
@Service
public class TokenService {

    @Value("${JWT_TOKEN_KEY}")
    private String tokenKey;

    @Autowired
    private UsersRepository usersRepository;

//    public TokenService(){
//
//    }

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
            DecodedJWT decodedJWT = JWT.decode(token);
            String userName = decodedJWT.getSubject();

            log.debug("Decoded username from token: {}", userName);
            Users user = usersRepository.findByLogin(userName)
                            .orElseThrow(() -> new UsernameNotFoundException("User not found: " + userName));

            System.out.println("RefreshToken");

            String newToken = generateToken(user);

            return new LoginResponseTokenRepModel(
                    newToken,
                    user.getLogin(),
                    LocalDateTime.now().plusMinutes(2).atOffset(ZoneOffset.of("-03:00")).toLocalDateTime(),
                    user.getRole());
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

    private Instant genExpirationDate(){
        return LocalDateTime.now().plusMinutes(3).toInstant(ZoneOffset.of("-03:00"));
    }

}
