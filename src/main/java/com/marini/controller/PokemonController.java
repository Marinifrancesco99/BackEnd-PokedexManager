package com.marini.controller;

import java.util.List;
import java.util.Map;

import com.marini.model.Pokemon;
import com.marini.service.PokemonService;

import io.javalin.Javalin;
import io.javalin.http.HttpStatus;

public class PokemonController {

    private PokemonService pokemonService = new PokemonService();
    private String apiVersionV1 = "/api/v1";

    public void registerRoutes(Javalin app) {

        // app.get(apiVersionV1 + "/pokemons", ctx -> {
        // List<Pokemon> pokemons = pokemonService.getAllPokemon();
        // ctx.json(pokemons);
        // });

        // Definisco le rotte
        app.get("/api/v1/protected/pokemons", ctx -> {
            // Recupera l'ID utente come Integer, che può essere null
            Integer userId = ctx.attribute("userId");

            if (userId == null) {
                ctx.status(401).json(Map.of("error", "Accesso non autorizzato."));
                return;
            }

            // Converte l'ID utente in un int per proseguire dopo aver visto se era null.
            int id = userId;

            List<Pokemon> pokemons = pokemonService.getAllPokemon();
            
            ctx.json(pokemons);

        });

        app.get(apiVersionV1 + "/pokemons/{national_number}", ctx -> {
            try {
                int national_number = Integer.parseInt(ctx.pathParam("national_number"));
                Pokemon pokemon = pokemonService.getByNationalNumber(national_number);
                if (pokemon != null) {
                    ctx.json(pokemon); // Mi restituisce il pokemon.
                } else {
                    ctx.status(HttpStatus.NOT_FOUND).json("Pokemon non trovato.");
                }
            } catch (IllegalArgumentException e) {
                ctx.status(HttpStatus.BAD_REQUEST).json("National number non valido.");
            }

        });

    }

}
