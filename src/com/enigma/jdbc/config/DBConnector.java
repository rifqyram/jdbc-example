package com.enigma.jdbc.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnector {

    public static Connection getConnection() {
        String url = "jdbc:postgresql://localhost:5432/enigma_shop";
        String user = System.getenv("DB_USERNAME"); // Your Username
        String password = System.getenv("DB_PASSWORD"); // Your Password

        try {
            Class.forName("org.postgresql.Driver");
            Connection connection = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to the PostgreSQL server successfully.");

            return connection;
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException("Error connecting to the database", e);
        }
    }
}
