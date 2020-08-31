package com.mikhailkarpov.demo.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtServiceImpl implements JwtService {

    private JwtSettings jwtSettings;

    public JwtServiceImpl(JwtSettings jwtSettings) {
        this.jwtSettings = jwtSettings;
    }

    @Override
    public String generateToken(String username) {
        Date issuedAt = new Date();
        Date expiresAt = new Date(System.currentTimeMillis() + 1000 * 60 * jwtSettings.getExpiresInMin());

        return Jwts.builder()
                .setSubject(username)
                .setIssuer(jwtSettings.getIssuer())
                .setIssuedAt(issuedAt)
                .setExpiration(expiresAt)
                .signWith(SignatureAlgorithm.HS256, jwtSettings.getSecret())
                .compact();
    }

    @Override
    public String validateAndGetUsername(String jwt) throws JwtValidationException {
        return getClaims(jwt).getSubject();
    }

    private Claims getClaims(String jwt) {
        try {
            return Jwts.parser()
                    .setSigningKey(jwtSettings.getSecret())
                    .parseClaimsJws(jwt)
                    .getBody();
        } catch (JwtException | IllegalArgumentException e) {
            throw new JwtValidationException("JWT cannot be trusted", e);
        }
    }
}
