package com.yourlife.your.life.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.yourlife.your.life.constants.ExceptionMessages;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenUtils {
    @Value("${service.jwt}")
    private String secret;

    public String generateToken(String id){
        try {
            var algoritmo = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withIssuer("API your.life")
                    .withSubject(id)
                    .withExpiresAt(dataExpiracao())
                    .sign(algoritmo);
        } catch (JWTCreationException exception){
            throw new RuntimeException(ExceptionMessages.TOKEN_GENERATION_ERROR, exception);
        }
    }

    public String getSubjects(String tokenJWT){
        try {
            var algoritmo = Algorithm.HMAC256(secret);
            var result = JWT.require(algoritmo)
                    .withIssuer("API your.life")
                    .build()
                    .verify(tokenJWT)
                    .getSubject();

            return result;
        } catch (JWTVerificationException exception) {
            throw new RuntimeException(ExceptionMessages.INVALID_TOKEN);
        }
    }
    private Instant dataExpiracao() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}
