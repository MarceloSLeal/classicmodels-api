package com.classicmodels.api.controller;

import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.classicmodels.api.model.LoginResponseTokenRepModel;
import com.classicmodels.api.model.input.AuthInput;
import com.classicmodels.api.model.input.UsersInput;
import com.classicmodels.domain.model.Users;
import com.classicmodels.domain.repository.UsersRepository;
import com.classicmodels.security.TokenService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@AllArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private AuthenticationManager authenticationManager;
    private UsersRepository usersRepository;
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseTokenRepModel> login(@RequestBody @Valid AuthInput authInput) {
        var userNamePassword = new UsernamePasswordAuthenticationToken(authInput.getLogin(), authInput.getPassword());
        var auth = this.authenticationManager.authenticate(userNamePassword);

        var token = tokenService.generateToken((Users) auth.getPrincipal());

        if (token.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(new LoginResponseTokenRepModel(token, authInput.getLogin(),
                LocalDateTime.now().plusMinutes(3).atOffset(ZoneOffset.of("-03:00")).toLocalDateTime()));

    }

    @PostMapping("/refresh")
    public ResponseEntity<LoginResponseTokenRepModel> refresh(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String token = authorizationHeader.replace("Bearer ", "");

        try {
            System.out.println(token);
            return ResponseEntity.ok(tokenService.refreshToken(token));

        } catch (JWTVerificationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid UsersInput usersInput) {
        if (this.usersRepository.findByLogin(usersInput.getLogin()) != null) return ResponseEntity.badRequest().build();

        String encryptedPassword = new BCryptPasswordEncoder().encode(usersInput.getPassword());
        Users newUser = new Users(usersInput.getLogin(), encryptedPassword, usersInput.getRole());

        System.out.println(newUser.getPassword());

        this.usersRepository.save(newUser);

        return ResponseEntity.ok().build();
    }

}
