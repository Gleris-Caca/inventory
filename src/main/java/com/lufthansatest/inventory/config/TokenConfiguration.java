package com.lufthansatest.inventory.config;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.crypto.SecretKey;

@Configuration
public class TokenConfiguration {
    @Bean
    public SecretKey secretKey(){
        return Keys //from the io.jsonwebtoken library
                .secretKeyFor(SignatureAlgorithm.HS256); // generates a secret key
    }
}