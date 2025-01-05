package com.marini.auth.middleware;

import java.util.Map;

import com.marini.auth.utility.AuthUtil;

import io.javalin.Javalin;

public class AuthMiddleware {

    // Metodo per applicare la protezione tramite JWT
    public static void apply(Javalin app) {
        app.before("/api/v1/protected/*", ctx -> {
            String token = ctx.header("Authorization");

            // Verifica se il token è presente e ha il prefisso "Bearer"
            if (token == null || !token.startsWith("Bearer ")) {
                ctx.status(401).json(Map.of("error", "Token mancante o non valido."));
                return;
            }

            // Rimuove il prefisso "Bearer " dal token
            token = token.replace("Bearer ", "");
            try {
                // Verifica il token JWT e ottieni il subject (l'ID dell'utente come String)
                String userIdString = AuthUtil.verifyToken(token);

                // Converti il subject (ID utente) da String a int
                try {
                    int userId = Integer.parseInt(userIdString);  // Conversione da String a int
                    ctx.attribute("userId", userId);          // Passa l'ID dell'utente al contesto
                } catch (NumberFormatException e) {
                    // Se non riesci a convertire, il token è non valido
                    ctx.status(401).json(Map.of("error", "Token non valido. ID utente non valido."));
                    return;
                }

            } catch (Exception e) {
                // Se il token non è valido
                ctx.status(401).json(Map.of("error", "Token non valido."));
            }
        });
    }
}

