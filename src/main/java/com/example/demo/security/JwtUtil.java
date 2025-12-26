package com.example.demo.security;

import com.example.demo.entity.UserAccount;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;

@Component
public class JwtUtil {
    private SecretKey secretKey;
    private final long EXPIRATION_TIME = 1000 * 60 * 60 * 10;

    @PostConstruct
    public void initKey() {
        this.secretKey = Jwts.SIG.HS256.key().build(); 
    }

    public String generateToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .claims(claims)
                .subject(subject)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(secretKey)
                .compact();
    }

    public String generateTokenForUser(UserAccount user) {
        return generateToken(
            Map.of("role", user.getRole(), "userId", user.getId(), "email", user.getEmail()),
            user.getEmail()
        );
    }

    // Pass Tests t69-t72 which call .getPayload()
    public Jws<Claims> parseToken(String token) {
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token);
    }

    public String extractUsername(String token) {
        return parseToken(token).getPayload().getSubject();
    }

    public String extractRole(String token) {
        return parseToken(token).getPayload().get("role", String.class);
    }

    public Long extractUserId(String token) {
        return parseToken(token).getPayload().get("userId", Long.class);
    }

    public boolean isTokenValid(String token, String email) {
        try {
            return extractUsername(token).equals(email) && !parseToken(token).getPayload().getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }
    
    // Helper needed for JwtAuthenticationFilter
    public String extractEmail(String token) { return extractUsername(token); }
    public boolean validateToken(String token, String email) { return isTokenValid(token, email); }
}