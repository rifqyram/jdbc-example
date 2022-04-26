package com.enigma.jdbc.product;

import com.enigma.jdbc.config.DBConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductDao {

    public void insertProduct(String id, String name, Integer price, Integer stock) throws SQLException {
        Connection connection = DBConnector.getConnection();
        String query = "INSERT INTO product (id, product_name, product_price, stock) VALUES (?, ?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(query);

        preparedStatement.setString(1, id);
        preparedStatement.setString(2, name);
        preparedStatement.setInt(3, price);
        preparedStatement.setInt(4, stock);

        preparedStatement.execute();

        preparedStatement.close();
        connection.close();
    }

    public String getProductById(String productId) throws SQLException {
        Connection connection = DBConnector.getConnection();
        String query = "SELECT * FROM product WHERE id = ?";

        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, productId);

        ResultSet resultSet = preparedStatement.executeQuery();

        String product = null;
        if (resultSet.next()) {
            String id = resultSet.getString("id");
            String productName = resultSet.getString("product_name");
            Integer productPrice = resultSet.getInt("product_price");
            Integer stock = resultSet.getInt("stock");

            product = String.format("%-40s %-30s %-30d %-30d", id, productName, productPrice, stock);
        }

        resultSet.close();
        preparedStatement.close();
        connection.close();

        return  product;
    }

    public void deleteProductById(String productId) throws SQLException {
        Connection connection = DBConnector.getConnection();

        String query = "DELETE FROM product WHERE id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, productId);
        preparedStatement.executeUpdate();

        preparedStatement.close();
        connection.close();
    }

}