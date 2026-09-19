package org.ong.dryforest.security;

import java.util.Base64;
import java.util.Date;
import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;

@Component
public class JwtUtils {
    @Value("${jwt.secret}")
    private String jwtSecret;
    @Value("${jwt.expiration.ms}")
    private long jwtExpirationMs;

    private Key key() {
        byte[] decodedKey = Base64.getDecoder().decode(jwtSecret);
        return Keys.hmacShaKeyFor(decodedKey);
    }

    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
                .signWith(key(), SignatureAlgorithm.HS256)
                .compact();
    }

    public LocalDateTime getExpirationDateFromJwt(String token) {
        Date exp = Jwts.parserBuilder()
                       .setSigningKey(key())
                       .build()
                       .parseClaimsJws(token)
                       .getBody()
                       .getExpiration();
        return LocalDateTime.ofInstant(exp.toInstant(), ZoneId.systemDefault());
    }


    public String getUsernameFromJwt(String token) {
        return Jwts.parserBuilder().setSigningKey(key()).build()
                .parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateJwtToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key()).build().parseClaimsJws(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }
}
