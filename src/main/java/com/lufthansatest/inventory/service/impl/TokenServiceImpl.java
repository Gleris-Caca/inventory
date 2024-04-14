package com.lufthansatest.inventory.service.impl;

import com.lufthansatest.inventory.model.enums.ClaimIdentifier;
import com.lufthansatest.inventory.service.TokenService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class TokenServiceImpl implements TokenService {

    private final Key key;

    public TokenServiceImpl(Key key) {
        this.key = key;
    }

    @Override
    public String generateToken(UserDetails userDetails) {
        final Map<String, Object> claims = new HashMap<>(); // creates a new HashMap object named claims to store the claims for the token
        claims.put(ClaimIdentifier.AUTHORITIES.name(), userDetails
                .getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList()));
        return createToken(claims, userDetails.getUsername());
    }

    @Override
    public Boolean validateToken(String token, UserDetails user) {
        return extractEmail(token).equals(user.getUsername()) && !isTokenExpired(token);
    }

    @Override
    public String extractEmail(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        return claimsResolver.apply(extractAllClaims(token));
    } //Using Generics, it is possible to create classes that work with different data types

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)//sets the signing key used to verify the token's authenticity
                .build()
                .parseClaimsJws(token)
                .getBody(); //the claims of the token
    }

    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)//sets the claims for the token.
                .setSubject(subject) //sets the subject of the token
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 5L * 60 * 60 * 1000))
                .signWith(key) //signs the token using the provided key
                .compact(); // generates the final compact JWT string representation of the token.
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }
}