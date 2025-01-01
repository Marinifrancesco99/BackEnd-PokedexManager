package com.marini.controller;

import com.marini.dao.UsersDao;
import com.marini.model.Users;

import io.javalin.Javalin;

public class UsersController {

    private static UsersDao userDao = new UsersDao();
    private String apiVersionV1 = "/api/v1";

    // Definisci il gestore per il login separatamente
    private static io.javalin.http.Handler login = ctx -> {
        // Recupera i dati dal corpo della richiesta
        String username = ctx.formParam("username");
        String password = ctx.formParam("password");

        // Verifica le credenziali usando UsersDao
        Users user = userDao.getUserByusername(username);

        if (user != null && user.getPassword().equals(password)) {
            // Salva l'utente nella sessione
            ctx.sessionAttribute("id_user", user.getId_user());
            ctx.result("Login effettuato con successo!");
        } else {
            ctx.status(401).result("Credenziali errate.");
        }
    };

    // Metodo per registrare le rotte
    public void registerRoutes(Javalin app) {
        app.post(apiVersionV1 + "/login", login);  // Usa l'handler login definito precedentemente
    }
}
