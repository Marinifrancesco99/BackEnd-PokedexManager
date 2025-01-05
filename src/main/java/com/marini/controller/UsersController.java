package com.marini.controller;

import java.util.Map;

import com.marini.auth.utility.AuthUtil;
import com.marini.model.Users;
import com.marini.service.UsersService;

import io.javalin.Javalin;
import io.javalin.http.HttpStatus;

public class UsersController {

    private static UsersService usersService = new UsersService();
    private String apiVersionV1 = "/api/v1";

    // Definisci il gestore per il login separatamente
    private static io.javalin.http.Handler login = ctx -> {
        // Recupera i dati dal corpo della richiesta
        String username = ctx.formParam("username");
        String password = ctx.formParam("password");

        // Verifica le credenziali usando UsersDao
        Users user = usersService.getUserByusername(username);
        if (user != null && user.getPassword().equals(password)) {
            // Salva l'utente nella sessione
            // ctx.sessionAttribute("id_user", user.getId_user());
            // ctx.result("Login effettuato con successo!");
            // Genera JWT o utilizza sessione
            String token = AuthUtil.generateToken(String.valueOf(user.getId_user()));
            ctx.status(200).json(Map.of(
                    "message", "Login effettuato con successo!",
                    "token", token));
        } else if (user == null) {
            ctx.status(HttpStatus.NOT_FOUND).json("Utente non trovato.");
        } else {
            ctx.status(HttpStatus.UNAUTHORIZED).json("Credenziali errate.");
        }
    };

    // Metodo per registrare le rotte
    public void registerRoutes(Javalin app) {
        app.post(apiVersionV1 + "/login", login); // Usa l'handler login definito precedentemente
    }
}
