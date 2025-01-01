package com.marini.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.marini.model.Pokemon;
import com.marini.utility.DatabaseConnection;

public class PokemonDao {

    private Connection connection = DatabaseConnection.getInstance().getConnection();

    public List<Pokemon> getAllPokemon() {

        // Dichiarazione della query SQL per ottenere tutti i record dalla tabella
        // "pokemon".
        String getAllPokemonSQL = "SELECT * FROM pokemon";

        // Creo la lista che conterrà gli elementi presi dal DB.
        List<Pokemon> pokemons = new ArrayList<>();

        try {
            // Creazione di un oggetto statemant per eseguire le query sul Database.
            Statement statement = connection.createStatement();

            // Esecuzione della query SQL e momorizzo i risultati su una result.
            ResultSet result = statement.executeQuery(getAllPokemonSQL);

            // Itera su ciascuna riga di result(result sarebbe il pokemon preso dal db).
            while (result.next()) {

                pokemons.add(
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

        return pokemons;

    }

    public Pokemon getByNationalNumber(int national_number) {

        String getByNationalNumberSQL = "SELECT * FROM pokemon WHERE national_number = ?";

        try {
            PreparedStatement preparedStatementNationalNumber = connection.prepareStatement(getByNationalNumberSQL);

            preparedStatementNationalNumber.setObject(1, national_number);

            ResultSet result = preparedStatementNationalNumber.executeQuery();

            if (result.next()) {
                
                return new Pokemon(
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
                    result.getString("description")
                );
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null; // Se nessun pokemon viene trovato.

    }

}
