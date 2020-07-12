package com.mikhailkarpov.demo.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtServiceImpl implements JwtService {

    private static final Logger log = LoggerFactory.getLogger(JwtServiceImpl.class);
    private JwtSettings jwtSettings;
    private UserDetailsService userDetailsService;
    private AuthenticationManager authenticationManager;

    public JwtServiceImpl(JwtSettings jwtSettings, UserDetailsService userDetailsService, AuthenticationManager authenticationManager) {
        this.jwtSettings = jwtSettings;
        this.userDetailsService = userDetailsService;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public String generateToken(UserDetails userDetails) {
        Date issuedAt = new Date();
        Date expiresAt = new Date(System.currentTimeMillis() + 1000 * 60 * jwtSettings.getExpiresInMin());

        String jwt = Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuer(jwtSettings.getIssuer())
                .setIssuedAt(issuedAt)
                .setExpiration(expiresAt)
                .signWith(SignatureAlgorithm.HS256, jwtSettings.getSecret())
                .compact();

        log.debug("JWT generated for {}: {}", userDetails, jwt);
        return jwt;
    }

    @Override
    public String getUsername(String jwt) throws JwtValidationException {
        return getClaims(jwt).getSubject();
    }

    @Override
    public boolean isValid(String jwt, UserDetails userDetails) {
        Claims claims = getClaims(jwt);

        if (!userDetails.getUsername().equals(claims.getSubject())) {
            System.out.println("invalid subject");
            return false;
        }

        if (claims.getExpiration().before(new Date())) {
            System.out.println("expired token");
            return false;
        }

        return true;
    }

    private Claims getClaims(String jwt) {
        try {
            return Jwts.parser()
                    .setSigningKey(jwtSettings.getSecret())
                    .parseClaimsJws(jwt)
                    .getBody();

        } catch (JwtException e) {
            throw new JwtValidationException("JWT cannot be trusted", e);
        }
    }
}
