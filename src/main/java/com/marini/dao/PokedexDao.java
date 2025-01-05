package com.marini.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.marini.model.Pokemon;
import com.marini.utility.DatabaseConnection;

public class PokedexDao {

    private Connection connection = DatabaseConnection.getInstance().getConnection();

    public boolean addToPokedex(int national_number, int userId) {
        // Logica aggiornata per includere l'ID utente
        String checkPokemonExistsSQL = "SELECT * FROM pokemon WHERE national_number = ?";
        String checkPokedexExistsSQL = "SELECT * FROM pokedex WHERE national_number = ? AND id_user = ?";
        String addToPokedexSQL = "INSERT INTO pokedex (national_number, id_user) VALUES (?, ?)";

        try {
            // Verifica se il Pokémon esiste
            PreparedStatement checkPokemonExistsStmt = connection.prepareStatement(checkPokemonExistsSQL);
            checkPokemonExistsStmt.setInt(1, national_number);
            ResultSet pokemonResult = checkPokemonExistsStmt.executeQuery();
            if (!pokemonResult.next()) {
                return false;
            }

            // Verifica se il Pokémon è già nella pokedex dell'utente
            PreparedStatement checkPokedexExistsStmt = connection.prepareStatement(checkPokedexExistsSQL);
            checkPokedexExistsStmt.setInt(1, national_number);
            checkPokedexExistsStmt.setInt(2, userId);
            ResultSet pokedexResult = checkPokedexExistsStmt.executeQuery();
            if (pokedexResult.next()) {
                return false;
            }

            // Aggiungi il Pokémon alla pokedex per l'utente
            PreparedStatement addToPokedexStmt = connection.prepareStatement(addToPokedexSQL);
            addToPokedexStmt.setInt(1, national_number);
            addToPokedexStmt.setInt(2, userId);
            int rowsAffected = addToPokedexStmt.executeUpdate();

            return rowsAffected > 0;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Pokemon> getAllPokedexPokemon(int userId) {

        // Dichiarazione della query SQL per ottenere tutti i record dalla tabella
        // pokedex.
        String getAllPokemonSQL = "SELECT p.national_number, p.gen, p.english_name, p.primary_type, p.secondary_type, "
                +
                "p.classification, p.percent_male, p.percent_female, p.height_m, p.weight_kg, " +
                "p.capture_rate, p.hp, p.attack, p.defense, p.speed, " +
                "p.abilities_0, p.abilities_1, p.abilities_special, " +
                "p.is_sublegendary, p.is_legendary, p.is_mythical, " +
                "p.evochain_0, p.evochain_2, p.evochain_4, p.mega_evolution, p.description " +
                "FROM pokemon p " +
                "JOIN pokedex pd ON p.national_number = pd.national_number " +
                "WHERE pd.id_user = ?";

        // Creo la lista che conterrà gli elementi presi dal DB.
        List<Pokemon> pokedex = new ArrayList<>();

        try {
            // Uso PreparedStatement per gestire i parametri in modo sicuro
            PreparedStatement preparedStatement = connection.prepareStatement(getAllPokemonSQL);
            preparedStatement.setInt(1, userId); // Imposta il parametro dell'utente

            // Esecuzione della query SQL e memorizzo i risultati su un result set.
            ResultSet result = preparedStatement.executeQuery();

            // Itera su ciascuna riga del result
            while (result.next()) {
                pokedex.add(
                        // Creazione di un oggetto pokemon contenente i dati ottenuti dal DB.
                        new Pokemon(
                                result.getInt("national_number"),
                                result.getString("gen"),
                                result.getString("english_name"),
                                result.getString("primary_type"),
                                result.getString("secondary_type"),
                                result.getString("classification"),
                                result.getDouble("percent_male"),
                                result.getDouble("percent_female"),
                                result.getDouble("height_m"),
                                result.getDouble("weight_kg"),
                                result.getInt("capture_rate"),
                                result.getInt("hp"),
                                result.getInt("attack"),
                                result.getInt("defense"),
                                result.getInt("speed"),
                                result.getString("abilities_0"),
                                result.getString("abilities_1"),
                                result.getString("abilities_special"),
                                result.getInt("is_sublegendary"),
                                result.getInt("is_legendary"),
                                result.getInt("is_mythical"),
                                result.getString("evochain_0"),
                                result.getString("evochain_2"),
                                result.getString("evochain_4"),
                                result.getString("mega_evolution"),
                                result.getString("description")));
            }

        } catch (SQLException e) {
            // Se si verifica un'eccezione durante l'interazione con il database, viene
            // lanciata una RuntimeException.
            throw new RuntimeException(e);
        }
        return pokedex;
    }

}
