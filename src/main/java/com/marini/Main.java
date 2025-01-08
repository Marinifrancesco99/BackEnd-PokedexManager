package com.marini;

import com.marini.auth.middleware.AuthMiddleware;
import com.marini.controller.PokemonController;
import com.marini.controller.UsersController;

import io.javalin.Javalin;
import io.javalin.plugin.Plugin;


public class Main {
    public static void main(String[] args) {
        
        Javalin app = Javalin.create(config -> {
            // Abilita CORS per l'app
            config.plugins.register(new Plugin() {
                @Override
                public void apply(Javalin app) {
                    app.before((ctx) -> {
                        ctx.header("Access-Control-Allow-Origin", "http://localhost:5173");  // URL del tuo frontend
                        ctx.header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE");
                        ctx.header("Access-Control-Allow-Headers", "Content-Type, Authorization");
                        ctx.header("Access-Control-Allow-Credentials", "true");  // Se usi i cookie
                    });
                }
            });
        }).start(8001);

        PokemonController pokemonController = new PokemonController();
        pokemonController.registerRoutes(app);

        UsersController usersController = new UsersController();
        usersController.registerRoutes(app);

        AuthMiddleware authMiddleware = new AuthMiddleware();
        authMiddleware.apply(app);

        System.out.println("Server is running on port 8001");
        
    }
}
