package com.classicmodels.api.controller;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.classicmodels.api.model.TokenRepModel;
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

@AllArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private AuthenticationManager authenticationManager;
    private UsersRepository usersRepository;
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<TokenRepModel> login(@RequestBody @Valid AuthInput authInput) {
        var userNamePassword = new UsernamePasswordAuthenticationToken(authInput.getLogin(), authInput.getPassword());
        var auth = this.authenticationManager.authenticate(userNamePassword);

        TokenRepModel token = tokenService.generateToken((Users) auth.getPrincipal());

        if (token == null) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(token);
    }

    @PostMapping("/refresh")
    public ResponseEntity<TokenRepModel> refresh(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String token = authorizationHeader.replace("Bearer ", "");

        try {
            return ResponseEntity.ok(tokenService.refreshToken(token));

        } catch (JWTVerificationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody @Valid UsersInput usersInput) {
//        if (this.usersRepository.findByLogin(usersInput.getLogin()).isPresent()) {
        if (this.usersRepository.findByLogin(usersInput.getLogin()) != null) {
            return ResponseEntity.badRequest().body("This login is already in use");
        }

        String encryptedPassword = new BCryptPasswordEncoder().encode(usersInput.getPassword());
        Users newUser = new Users(usersInput.getLogin(), encryptedPassword, usersInput.getRole());

        this.usersRepository.save(newUser);
        return ResponseEntity.ok().build();
    }

}
