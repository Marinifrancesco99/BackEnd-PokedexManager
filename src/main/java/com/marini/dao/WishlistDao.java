package com.marini.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.marini.model.Pokemon;
import com.marini.utility.DatabaseConnection;

public class WishlistDao {
    private Connection connection = DatabaseConnection.getInstance().getConnection();

    public boolean addToWishlist(int national_number, int userId) {
        // Logica aggiornata per includere l'ID utente
        String checkPokemonExistsSQL = "SELECT * FROM pokemon WHERE national_number = ?";
        String checkWishlistExistsSQL = "SELECT * FROM wishlist WHERE national_number = ? AND id_user = ?";
        String addToWishlistSQL = "INSERT INTO wishlist (national_number, id_user) VALUES (?, ?)";

        try {
            // Verifica se il Pokémon esiste
            PreparedStatement checkPokemonExistsStmt = connection.prepareStatement(checkPokemonExistsSQL);
            checkPokemonExistsStmt.setInt(1, national_number);
            ResultSet pokemonResult = checkPokemonExistsStmt.executeQuery();
            if (!pokemonResult.next()) {
                return false;
            }

            // Verifica se il Pokémon è già nella wishlist dell'utente
            PreparedStatement checkWishlistExistsStmt = connection.prepareStatement(checkWishlistExistsSQL);
            checkWishlistExistsStmt.setInt(1, national_number);
            checkWishlistExistsStmt.setInt(2, userId);
            ResultSet wishlistResult = checkWishlistExistsStmt.executeQuery();
            if (wishlistResult.next()) {
                return false;
            }

            // Aggiungi il Pokémon alla wishlist per l'utente
            PreparedStatement addToWishlistStmt = connection.prepareStatement(addToWishlistSQL);
            addToWishlistStmt.setInt(1, national_number);
            addToWishlistStmt.setInt(2, userId);
            int rowsAffected = addToWishlistStmt.executeUpdate();

            return rowsAffected > 0;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Pokemon> getAllWishlistPokemon(int userId) {
        // Dichiarazione della query SQL per ottenere tutti i record dalla tabella
        // wishlist
        String getAllPokemonSQL = "SELECT p.national_number, p.gen, p.english_name, p.primary_type, p.secondary_type, "
                +
                "p.classification, p.percent_male, p.percent_female, p.height_m, p.weight_kg, " +
                "p.capture_rate, p.hp, p.attack, p.defense, p.speed, " +
                "p.abilities_0, p.abilities_1, p.abilities_special, " +
                "p.is_sublegendary, p.is_legendary, p.is_mythical, " +
                "p.evochain_0, p.evochain_2, p.evochain_4, p.mega_evolution, p.description " +
                "FROM pokemon p " +
                "JOIN wishlist w ON p.national_number = w.national_number " +
                "WHERE w.id_user = ?";

        // Creo la lista che conterrà gli elementi presi dal DB
        List<Pokemon> wishlist = new ArrayList<>();

        try {
            // Uso PreparedStatement per gestire i parametri in modo sicuro
            PreparedStatement preparedStatement = connection.prepareStatement(getAllPokemonSQL);
            preparedStatement.setInt(1, userId); // Imposta il parametro dell'utente

            // Esecuzione della query SQL e memorizzo i risultati su un result set
            ResultSet result = preparedStatement.executeQuery();

            // Itera su ciascuna riga del result
            while (result.next()) {
                wishlist.add(
                        // Creazione di un oggetto Pokemon contenente i dati ottenuti dal DB
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
            throw new RuntimeException(e);
        }
        return wishlist;
    }

    public boolean removeFromWishlist(int national_number, int userId) {
        // SQL per verificare se il Pokémon è presente nella wishlist dell'utente
        String checkWishlistExistsSQL = "SELECT * FROM wishlist WHERE national_number = ? AND id_user = ?";

        // SQL per rimuovere il Pokémon dalla wishlist
        String deleteFromWishlistSQL = "DELETE FROM wishlist WHERE national_number = ? AND id_user = ?";

        try {
            // Verifica se il Pokémon è presente nella wishlist dell'utente
            PreparedStatement checkWishlistExistsStmt = connection.prepareStatement(checkWishlistExistsSQL);
            checkWishlistExistsStmt.setInt(1, national_number);
            checkWishlistExistsStmt.setInt(2, userId);
            ResultSet wishlistResult = checkWishlistExistsStmt.executeQuery();

            // Se il Pokémon non è nella wishlist, ritorna false
            if (!wishlistResult.next()) {
                return false;
            }

            // Rimuovi il Pokémon dalla wishlist dell'utente
            PreparedStatement deleteFromWishlistStmt = connection.prepareStatement(deleteFromWishlistSQL);
            deleteFromWishlistStmt.setInt(1, national_number);
            deleteFromWishlistStmt.setInt(2, userId);
            int rowsAffected = deleteFromWishlistStmt.executeUpdate();

            return rowsAffected > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Errore durante la rimozione del Pokémon dalla wishlist", e);
        }
    }

}
