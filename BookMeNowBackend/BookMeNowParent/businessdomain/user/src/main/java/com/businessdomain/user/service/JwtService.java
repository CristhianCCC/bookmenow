package com.businessdomain.user.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

/**
 * Service responsible for generating, validating, and extracting information from JWT tokens.
 */
@Service
public class JwtService {

    /**
     * Secret key used to sign and verify JWT tokens.
     */
    private static final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    /**
     * Generates a JWT token with extra claims and a subject (username).
     *
     * @param extraClaims additional claims to include in the token
     * @param username the subject of the token (usually the user's email or username)
     * @return a signed JWT token
     */
    public String generateToken(Map<String, Object> extraClaims, String username) {
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 86400000)) // 1 day
                .signWith(SECRET_KEY, SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Extracts the username (subject) from the given JWT token.
     *
     * @param token the JWT token
     * @return the username extracted from the token
     */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Extracts a specific claim from the JWT token using the given resolver function.
     *
     * @param token the JWT token
     * @param resolver a function that extracts a specific claim
     * @param <T> the type of the extracted claim
     * @return the resolved claim
     */
    public <T> T extractClaim(String token, Function<Claims, T> resolver) {
        final Claims claims = Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return resolver.apply(claims);
    }

    /**
     * Validates a JWT token against the provided username.
     *
     * @param token the JWT token
     * @param userDetails the username to validate against the token's subject
     * @return true if the token is valid and matches the username
     */
    public boolean isTokenValid(String token, UserDetails userDetails) {
        String username = extractUsername(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }


    /**
     * Checks if the given JWT token is expired.
     *
     * @param token the JWT token
     * @return true if the token has expired
     */
    public boolean isTokenExpired(String token) {
        return extractClaim(token, Claims::getExpiration).before(new Date());
    }
}
