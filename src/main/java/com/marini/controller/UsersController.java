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

    // Registrazione Utente
    private static io.javalin.http.Handler register = ctx -> {
        // Recupera i dati dal corpo della richiesta
        String username = ctx.formParam("username");
        String email = ctx.formParam("email");
        String password = ctx.formParam("password");

        // Verifica che tutti i campi siano forniti
        if (username == null || email == null || password == null) {
            ctx.status(400).json(Map.of("error", "Tutti i campi sono obbligatori."));
            return;
        }

        // Controlla se l'utente esiste già
        Users existingUser = usersService.getUserByusername(username);
        if (existingUser != null) {
            ctx.status(409).json(Map.of("error", "Username già in uso."));
            return;
        }

        // Crea un nuovo utente
        Users newUser = new Users(0, username, email, password);

        // Salva il nuovo utente nel database
        boolean success = usersService.createUser(newUser);
        if (success) {
            ctx.status(201).json(Map.of("message", "Registrazione avvenuta con successo!"));
        } else {
            ctx.status(500).json(Map.of("error", "Errore durante la registrazione."));
        }
    };

    // Metodo per registrare le rotte
    public void registerRoutes(Javalin app) {
        app.post(apiVersionV1 + "/login", login); // Usa l'handler login definito precedentemente
        app.post(apiVersionV1 + "/register", register);
        app.delete(apiVersionV1 + "/users/{id}", ctx -> {
            int id = Integer.parseInt(ctx.pathParam("id"));
            UsersService usersService = new UsersService();
            boolean isDeleted = usersService.deleteUser(id);

            if (isDeleted) {
                ctx.status(200).result("Utente eliminato con successo.");
            } else {
                ctx.status(404).result("Utente non trovato o non eliminabile.");
            }
        });

    }

}
