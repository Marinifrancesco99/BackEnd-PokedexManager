package com.marini.utility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    
    // La variabile statica `instance` rappresenta l'unica istanza della classe `DatabaseConnection`.
    private static DatabaseConnection instance;

    // La variabile `connection` rappresenta l'oggetto di connessione al database.
    private Connection connection;

    // Dati del DB.
    private final String urlDB = "jdbc:postgresql://localhost:5432/pokedex-manager";

    private final String userDB = "postgres";

    private final String pwdDB = "francesco";

    // Costruttore privato: impedisce la creazione di nuove istanze al di fuori della classe stessa.
    private DatabaseConnection () {

        try {
            Class.forName("org.postgresql.Driver");
            
            //Creo la connessione al db in base ai parametri forniti.
            connection = DriverManager.getConnection(urlDB, userDB, pwdDB);

            //Abilita il commit automatico per ogni operazione eseguita sul database.
            connection.setAutoCommit(true);

        } catch (ClassNotFoundException | SQLException e) {

            throw new RuntimeException(e);
        }
    }

    // Metodo statico per ottenere l'istanza unica della classe
    public static DatabaseConnection getInstance () {

        if (instance == null) {
            instance = new DatabaseConnection();
        }

        return instance;
    }

    // Metodo pubblico per ottenere l'oggetto `Connection`.
    public Connection getConnection () {

        // Consente di accedere alla connessione al database dall'esterno della classe.
        return connection;
    }
    
}
