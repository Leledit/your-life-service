package com.yourlife.your.life.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.swing.text.Style;
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
            throw new RuntimeException("erro ao gerrar token jwt", exception);
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
            throw new RuntimeException("Token JWT inválido ou expirado!");
        }
    }
    private Instant dataExpiracao() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}
