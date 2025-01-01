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

    public Users getUserByusername (String username) {

        String getUserSQL = "SELECT * FROM users WHERE username = ?";

        try {
            PreparedStatement userStatement = connection.prepareStatement(getUserSQL);

            userStatement.setString(1,username);

            ResultSet userResult = userStatement.executeQuery();

            if (userResult.next()) {
                
                return new Users(
                    userResult.getInt("id_user"),
                    userResult.getString("username"),
                    userResult.getString("email"),
                    userResult.getString("password")
                );
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    
    }

}
