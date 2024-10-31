package com.api.MedSync.security.infrastructure.external;

import com.api.MedSync.security.domain.aggregate.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtProvider {

    @Value("${jwt.secret}")
    private String SECRET_KEY;

    public String getToken(UserDetails user) {
        Map<String, Object> claims = new HashMap<>();

        if (user instanceof User customUser) {
            claims.put("id", customUser.getId());
            claims.put("role", customUser.getRole());
        }

        return getToken(claims, user);
    }

    private String getToken(Map<String, Object> claims, UserDetails user) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(user.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 7)) // 7 days expiration
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Key getKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
    }

    public String getEmailFromToken(String token) {
        return getClaimFromToken(token, Claims.SUBJECT, String.class);
    }

    private Claims getClaimsFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public <T> T getClaimFromToken(String token, String claimName, Class<T> requiredType) {
        return getClaimsFromToken(token).get(claimName, requiredType);
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims.EXPIRATION, Date.class);
    }

    public boolean isTokenExpired(String token) {
        return getExpirationDateFromToken(token).before(new Date());
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        String email = getEmailFromToken(token);
        return email.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }
}