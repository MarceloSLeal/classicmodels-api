package com.classicmodels.security;

import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.Base64;

@Component
public class TokenGenerator {

    //Manter essa classe para talvez ir atualizando o token de tempos em tempos
    private final SecureRandom random = new SecureRandom();
    private final byte[] bytes = new byte[32];

    public String getToken() {
        random.nextBytes(bytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }

}
