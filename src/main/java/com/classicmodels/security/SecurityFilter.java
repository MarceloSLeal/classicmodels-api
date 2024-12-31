package com.classicmodels.security;

import com.classicmodels.domain.repository.UsersRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    TokenService tokenService;
    @Autowired
    UsersRepository usersRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String cookieName = determineCookieName(request);
        String token = this.recoverCookieToken(request, cookieName);

        if (token != null && !token.isEmpty()) {
            String validatedToken = tokenService.validateToken(token);

            if (validatedToken != null && !validatedToken.isEmpty()) {
                UserDetails user = usersRepository.findByLogin(validatedToken);
                var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } else {
                System.out.println("Token nao validado");
            }
        }

        filterChain.doFilter(request, response);
    }

    private String determineCookieName(HttpServletRequest request) {
        String path = request.getRequestURI();
        if (path.startsWith("/auth/refresh")) {
            return "refreshToken";
        }
        return "token";
    }

    private String recoverCookieToken(HttpServletRequest request, String cookieName) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookieName.equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return "";
    }
}