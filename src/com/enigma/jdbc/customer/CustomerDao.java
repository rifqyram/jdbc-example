package com.enigma.jdbc.customer;

import com.enigma.jdbc.config.DBConnector;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CustomerDao {

    public void insertCustomer(String id, String address, String birthDate, String mobilePhone, String name) throws SQLException {
        Connection connection = DBConnector.getConnection();

        String query = "INSERT INTO customer(id, address, birth_date, mobile_phone, name) VALUES ('" + id + "', '" + address + "', '" + birthDate + "', '" + mobilePhone + "', '" + name + "')";

        Statement statement = connection.createStatement();
        statement.execute(query);
        statement.close();
        connection.close();
    }

    public List<String> getAllCustomer() throws SQLException {
        Connection connection = DBConnector.getConnection();

        String query = "SELECT * FROM customer";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);

        List<String> customers = new ArrayList<>();

        System.out.printf("%-40s %-30s %-30s %-30s %-30s", "ID", "Address", "Birth Date", "Mobile Phone", "Name");
        while (resultSet.next()) {
            String id = resultSet.getString("id");
            String address = resultSet.getString("address");
            String birthDate = resultSet.getString("birth_date");
            String mobilePhone = resultSet.getString("mobile_phone");
            String name = resultSet.getString("name");

            String result = String.format("%-40s %-30s %-30s %-30s %-30s", id, address, birthDate, mobilePhone, name);
            customers.add(result);
        }

        statement.close();
        resultSet.close();
        connection.close();

        return customers;
    }

    public void updateCustomer(String id, String address, String birthDate, String mobilePhone, String name) throws SQLException {
        Connection connection = DBConnector.getConnection();

        String query = "UPDATE customer SET address = '" + address + "', birth_date = '" + birthDate + "', mobile_phone = '" + mobilePhone + "', name = '" + name + "' WHERE id = '" + id + "'";

        Statement statement = connection.createStatement();
        statement.executeUpdate(query);
        statement.close();
        connection.close();
    }

}
