package com.voteSphere.util;

import java.util.Date;
import java.util.function.Function;

import javax.crypto.SecretKey;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

public class JwtUtil {

    private static final String SECRET_KEY = "super-secret-E47C87FF-48EC-4FB2-ABDA-514CB4B1B365"; // min 256-bit
    private static final long EXPIRATION_TIME_MILLIS = 1000 * 60 * 15; // 15 minutes

    private static final SecretKey KEY = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

    // Generate token with subject and custom claims
    public String generateToken(String email, int userId) {
        return Jwts.builder()
            .subject(String.valueOf(userId)) // subject as userId
            .claim("email", email)
            .issuedAt(new Date())
            .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME_MILLIS))
            .signWith(KEY)
            .compact();
    }

    // Validate token and check expiration
    public boolean validateToken(String token) {
        try {
            Claims claims = extractAllClaims(token);
            return !claims.getExpiration().before(new Date());
        } catch (JwtException | IllegalArgumentException e) {
            return false; // Invalid token
        }
    }

    // Extract all claims from token
    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(KEY)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    // Extract specific claim
    public <T> T extractClaim(String token, Function<Claims, T> resolver) {
        return resolver.apply(extractAllClaims(token));
    }

    public String extractEmail(String token) {
        return extractClaim(token, claims -> claims.get("email", String.class));
    }

    public int extractUserID(String token) {
        return Integer.parseInt(extractClaim(token, Claims::getSubject));
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public Date extractIssuedAt(String token) {
        return extractClaim(token, Claims::getIssuedAt);
    }
}
