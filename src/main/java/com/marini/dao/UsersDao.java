package com.marini.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.marini.model.Users;
import com.marini.utility.DatabaseConnection;

//Recupero gli utenti dal database.
public class UsersDao {

    private Connection connection = DatabaseConnection.getInstance().getConnection();

    public Users getUserByusername(String username) {

        String getUserSQL = "SELECT * FROM users WHERE username = ?";

        try {
            PreparedStatement userStatement = connection.prepareStatement(getUserSQL);

            userStatement.setString(1, username);

            ResultSet userResult = userStatement.executeQuery();

            if (userResult.next()) {

                return new Users(
                        userResult.getInt("id_user"),
                        userResult.getString("username"),
                        userResult.getString("email"),
                        userResult.getString("password"));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;

    }

    public boolean createUser(Users user) {
        String insertUserSQL = "INSERT INTO users (username, email, password) VALUES (?, ?, ?)";

        try (PreparedStatement insertStatement = connection.prepareStatement(insertUserSQL)) {
            insertStatement.setString(1, user.getUsername());
            insertStatement.setString(2, user.getEmail());
            insertStatement.setString(3, user.getPassword());

            int rowsAffected = insertStatement.executeUpdate();
            return rowsAffected > 0; // Restituisce true se la registrazione è riuscita
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteUser(int userId) {
        String deleteUserSQL = "DELETE FROM users WHERE id_user = ?";
    
        try (PreparedStatement deleteStatement = connection.prepareStatement(deleteUserSQL)) {
            deleteStatement.setInt(1, userId);
    
            int rowsAffected = deleteStatement.executeUpdate();
            return rowsAffected > 0; // Restituisce true se l'eliminazione è riuscita
        } catch (SQLException e) {
            e.printStackTrace();
            return false;           // Gestisce eventuali errori di SQL
        }
    }

}
