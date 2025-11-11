package com.TraceCircle.RolesManagementService.Security;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JWTUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.access-expiration-ms}")
    private long accessExpMs;

    @Value("${jwt.refresh-expiration-ms}")
    private long refreshExpMs;

    private Key key() {
        
    	return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    public String generateAccessToken(String email) {
        
    	String token = Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + accessExpMs))
                .signWith(key(), SignatureAlgorithm.HS256)
                .compact();

        log.debug("Generated access token for {}", email);
       
        return token;
    }

    public String generateRefreshToken(String email) {
       
    	String token = Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + refreshExpMs))
                .signWith(key(), SignatureAlgorithm.HS256)
                .compact();

        log.debug("Generated refresh token for {}", email);
        
        return token;
    }

    public String extractUsername(String token) {
        
    	return Jwts.parserBuilder()
                .setSigningKey(key())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean isValidToken(String token, String email) {
        
    	try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(key())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            boolean ok = claims.getSubject().equals(email) &&
                         claims.getExpiration().after(new Date());

            if (!ok) log.warn("Invalid JWT for {}", email);

            return ok;
        } 
    	catch (JwtException | IllegalArgumentException e) {
           
    		log.warn("JWT validation error: {}", e.getMessage());
        
    		return false;
        }
    }
}
