package com.classicmodels.api.controller;

import com.classicmodels.api.model.LoginResponseTokenRepModel;
import com.classicmodels.api.model.input.AuthInput;
import com.classicmodels.api.model.input.UsersInput;
import com.classicmodels.domain.model.Users;
import com.classicmodels.domain.repository.UsersRepository;
import com.classicmodels.security.TokenService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.apache.catalina.User;
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
    public ResponseEntity login(@RequestBody @Valid AuthInput authInput) {
        var userNamePassword = new UsernamePasswordAuthenticationToken(authInput.getLogin(), authInput.getPassword());
        var auth = this.authenticationManager.authenticate(userNamePassword);

        var token = tokenService.generateToken((Users) auth.getPrincipal());

        return ResponseEntity.ok(new LoginResponseTokenRepModel(token));
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid UsersInput usersInput){
        if(this.usersRepository.findByLogin(usersInput.getLogin()) != null) return ResponseEntity.badRequest().build();

        String encryptedPassword = new BCryptPasswordEncoder().encode(usersInput.getPassword());
        Users newUser = new Users(usersInput.getLogin(), encryptedPassword, usersInput.getRole());

        System.out.println(newUser.getPassword());

        this.usersRepository.save(newUser);

        return ResponseEntity.ok().build();
    }

}
