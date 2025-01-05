package com.marini.auth.utility;


import java.util.Date;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;


public class AuthUtil {
    private static final String SECRET = "your_secret_key";
    private static final Algorithm ALGORITHM = Algorithm.HMAC256(SECRET);
    private static final long EXPIRATION_TIME = 3600_000; // 1 ora in millisecondi

    // Genera un token
    public static String generateToken(String username) {
        return JWT.create()
                .withSubject(username)  // Usa l'username come subject
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .sign(ALGORITHM);
    }

    // Verifica un token JWT
    public static String verifyToken(String token) {
        return JWT.require(ALGORITHM)
                .build()
                .verify(token)
                .getSubject(); // Restituisce il subject come String
    }
}