package com.kantor.kantor.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.kantor.kantor.entity.UserCredential;
import com.kantor.kantor.model.JwtClaims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;
import java.util.stream.Stream;

@Component
@Slf4j
public class JWTUtils {

    @Value("${app.kantor.jwt-secret}")
    private String secretKey;
    @Value("${app.kantor.jwt-expiration}")
    private Long expiration;
    @Value("${app.kantor.app-name}")
    private String appName;

    public String generationToken(UserCredential userCredential){
        try {
            Algorithm algorithm = Algorithm.HMAC512(secretKey);
            List<String> stream = userCredential.getRoles().stream()
                    .map(role -> role.getRole().name()).toList();
            return JWT.create()
                    .withIssuer(appName)
                    .withSubject(String.valueOf(userCredential.getId()))
                    .withExpiresAt(Instant.now().plusSeconds(expiration))
                    .withClaim("roles", stream)
                    .sign(algorithm);
        }catch (JWTCreationException e){
            throw new RuntimeException(e);
        }
    }

    public Boolean verifyJwtToken(String token){
        try {
            Algorithm algorithm = Algorithm.HMAC512(secretKey);
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT decodedJWT = verifier.verify(token);
            return decodedJWT.getIssuer().equals(appName);
        }catch (JWTVerificationException e){
            log.error("invalid verification JWT: {}", e.getMessage());
            return false;
        }
    }

    public JwtClaims getUserInfoByToken(String token){
        try {
            Algorithm algorithm = Algorithm.HMAC512(secretKey);
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT decodedJWT = verifier.verify(token);

            List<String> roles = decodedJWT.getClaim("roles").asList(String.class);

            return JwtClaims.builder()
                    .userId(decodedJWT.getSubject())
                    .roles(roles)
                    .build();
        }catch (JWTVerificationException e){
            log.error("invalid verification JWT: {}", e.getMessage());
            return null;
        }
    }
}
