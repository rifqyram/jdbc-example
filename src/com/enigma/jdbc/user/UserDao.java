package com.enigma.jdbc.user;

import com.enigma.jdbc.config.DBConnector;

import java.sql.*;

public class UserDao {

    public void sqlInjectionExample(String email, String password) throws SQLException {
        Connection connection = DBConnector.getConnection();
        String query = "SELECT * FROM users WHERE email = '" + email + "' AND password = '" + password + "'";

        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);

        if (resultSet.next()) {
            System.out.println("Login Success!!");
            System.out.println("Welcome " + resultSet.getString("name"));
        } else {
            System.out.println("Login Failed!!");
        }

        resultSet.close();
        statement.close();
        connection.close();
    }

    public void login(String email, String password) throws SQLException {
        Connection connection = DBConnector.getConnection();
        String query = "SELECT * FROM users WHERE email = ? AND password = ?";

        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, email);
        preparedStatement.setString(2, password);

        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            System.out.println("Login Success!!");
            System.out.println("Welcome " + resultSet.getString("name"));
        } else {
            System.out.println("Login Failed!!");
        }

        resultSet.close();
        preparedStatement.close();
        connection.close();
    }

}
