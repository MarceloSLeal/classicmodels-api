package com.classicmodels.api.controller;

import com.classicmodels.api.model.TokenRepModel;
import com.classicmodels.api.model.UserRepModel;
import com.classicmodels.api.model.input.AuthInput;
import com.classicmodels.api.model.input.UsersInput;
import com.classicmodels.domain.model.Users;
import com.classicmodels.domain.repository.UsersRepository;
import com.classicmodels.security.TokenService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private AuthenticationManager authenticationManager;
    private UsersRepository usersRepository;
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<UserRepModel> login(@RequestBody @Valid AuthInput authInput) {

        var userNamePassword = new UsernamePasswordAuthenticationToken(authInput.getLogin(), authInput.getPassword());
        var auth = this.authenticationManager.authenticate(userNamePassword);

        TokenRepModel token = tokenService.generateToken((Users) auth.getPrincipal());

        ResponseCookie tokenCookie = createCookie("token", token.getToken(), token.getExpires());
        ResponseCookie tokenRefreshCookie = createCookie("refreshToken", token.getRefreshToken(), token.getRefreshExpires());

        UserRepModel user = new UserRepModel(token.getUser(), token.getRole());

        return ResponseEntity.ok()
                .header("Set-Cookie", tokenCookie.toString())
                .header("Set-Cookie", tokenRefreshCookie.toString())
                .body(user);
    }

    @PostMapping("/refresh")
    public ResponseEntity<Void> refresh(HttpServletRequest request) {

        List<Cookie> cookies = Arrays.stream(request.getCookies()).toList();

        Cookie refreshTokenCookie = cookies.stream()
                .filter(cookie -> cookie.getName().equals("refreshToken"))
                .findFirst()
                .orElse(null);

        assert refreshTokenCookie != null;
        if (tokenService.validateToken(refreshTokenCookie.getValue()) == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        TokenRepModel token = tokenService.refreshToken(refreshTokenCookie.getValue());

        ResponseCookie tokenCookie = createCookie("token", token.getToken(), token.getExpires());
        ResponseCookie tokenRefreshCookie = createCookie("refreshToken", token.getRefreshToken(), token.getRefreshExpires());

        return ResponseEntity.ok()
                .header("Set-Cookie", tokenCookie.toString())
                .header("Set-Cookie", tokenRefreshCookie.toString())
                .build();
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody @Valid UsersInput usersInput) {

        if (this.usersRepository.findByLogin(usersInput.getLogin()) != null) {
            return ResponseEntity.badRequest().body("This login is already in use");
        }

        String encryptedPassword = new BCryptPasswordEncoder().encode(usersInput.getPassword());
        Users newUser = new Users(usersInput.getLogin(), encryptedPassword, usersInput.getRole());

        this.usersRepository.save(newUser);
        return ResponseEntity.ok().build();
    }

    private ResponseCookie createCookie(String tokenName, String token, LocalDateTime expireTime) {
        long maxAgeInSeconds = Duration.between(LocalDateTime.now(), expireTime).getSeconds();

        return ResponseCookie.from(tokenName, token)
                .httpOnly(true)
                .sameSite("Strict")
                .maxAge(maxAgeInSeconds)
                .path("/")
                .build();
    }
}
