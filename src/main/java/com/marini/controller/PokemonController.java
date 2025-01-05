package com.marini.controller;

import java.util.List;
import java.util.Map;

import com.marini.model.Pokemon;
import com.marini.service.PokedexService;
import com.marini.service.PokemonService;

import io.javalin.Javalin;
import io.javalin.http.HttpStatus;

public class PokemonController {

    private PokemonService pokemonService = new PokemonService();
    private PokedexService pokedexService = new PokedexService();
    private String apiVersionV1 = "/api/v1";

    public void registerRoutes(Javalin app) {

        // app.get(apiVersionV1 + "/pokemons", ctx -> {
        // List<Pokemon> pokemons = pokemonService.getAllPokemon();
        // ctx.json(pokemons);
        // });

        // Definisco le rotte

        // Rotta per vedere la lista di pokemon esistenti.
        app.get("/api/v1/protected/pokemons", ctx -> {
            // Recupera l'ID utente come Integer, che può essere null
            Integer userId = ctx.attribute("userId");

            if (userId == null) {
                ctx.status(401).json(Map.of("error", "Accesso non autorizzato."));
                return;
            }

            // Converte l'ID utente in un int per proseguire dopo aver visto se era null.
            //int id = userId;

            List<Pokemon> pokemons = pokemonService.getAllPokemon();
            
            ctx.json(pokemons);

        });

        // Rotta per accedere a un pokemon specifico.
        app.get(apiVersionV1 + "/protected/pokemons/{national_number}", ctx -> {

            Integer userId = ctx.attribute("userId");
            if (userId == null) {
                ctx.status(HttpStatus.UNAUTHORIZED).json("Accesso non autorizzato.");
                return;
            }

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


        // Rotta per aggiungere pokemon alla Pokedex
        app.post(apiVersionV1 + "/protected/pokedex/{national_number}", ctx -> {
            Integer userId = ctx.attribute("userId");
        
            if (userId == null) {
                ctx.status(HttpStatus.UNAUTHORIZED).json(Map.of("error", "Accesso non autorizzato."));
                return;
            }
        
            try {
                int national_number = Integer.parseInt(ctx.pathParam("national_number"));
        
                // Passa anche l'ID utente al servizio
                boolean added = pokedexService.addToPokedex(national_number, userId);
        
                if (added) {
                    ctx.status(HttpStatus.CREATED).json(Map.of(
                        "message", "Pokemon aggiunto alla pokedex con successo.",
                        "national_number", national_number));
                } else {
                    ctx.status(HttpStatus.BAD_REQUEST).json(Map.of(
                        "error", "Impossibile aggiungere il Pokemon. Potrebbe non esistere o essere già nella pokedex."));
                }
        
            } catch (NumberFormatException e) {
                ctx.status(HttpStatus.BAD_REQUEST).json(Map.of("error", "National number non valido."));
            } catch (Exception e) {
                ctx.status(HttpStatus.INTERNAL_SERVER_ERROR).json(Map.of(
                    "error", "Errore durante l'aggiunta del Pokemon alla pokedex.",
                    "details", e.getMessage()));
            }
        });
        


        // Rotta per vedere i pokemon presenti nella pokedex
        app.get(apiVersionV1 + "/protected/pokedex", ctx -> {
            Integer userId = ctx.attribute("userId");

            if (userId == null) {
                ctx.status(HttpStatus.UNAUTHORIZED).json(Map.of("error", "Accesso non autorizzato."));
                return;
            }

            List<Pokemon> pokemons = pokedexService.getAllPokedexPokemon(userId);

            ctx.json(pokemons);
        });

    }

}
