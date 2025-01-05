package com.marini;

import com.marini.auth.middleware.AuthMiddleware;
import com.marini.controller.PokemonController;
import com.marini.controller.UsersController;

import io.javalin.Javalin;


public class Main {
    public static void main(String[] args) {
        
        Javalin app = Javalin.create().start(8001);

        PokemonController pokemonController = new PokemonController();
        pokemonController.registerRoutes(app);

        UsersController usersController = new UsersController();
        usersController.registerRoutes(app);

        AuthMiddleware authMiddleware = new AuthMiddleware();
        authMiddleware.apply(app);

        System.out.println("Server is running on port 8001");
        
    }
}
