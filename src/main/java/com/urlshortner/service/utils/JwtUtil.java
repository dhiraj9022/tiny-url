package com.urlshortner.service.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {

    @Value("${jwt_secret}")
    private String secret;

    private String JWT_SUBJECT = "User Details";
    private String JWT_ISSUER = "Auth Service";

    public String generateToken(String email) {
        return JWT.create()
                .withSubject(JWT_SUBJECT)
                .withIssuer(JWT_ISSUER)
                .withClaim("email", email)
                .withIssuedAt(new Date())
                .sign(Algorithm.HMAC256(secret));
    }

    public String validateTokenAndRetrieveSub(String token) {
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(secret))
                .withSubject(JWT_SUBJECT)
                .withIssuer(JWT_ISSUER)
                .build();

        DecodedJWT jwt = jwtVerifier.verify(token);
        return jwt.getClaim("email").asString();
    }
}
