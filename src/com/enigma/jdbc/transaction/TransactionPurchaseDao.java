package com.enigma.jdbc.transaction;

import com.enigma.jdbc.config.DBConnector;

import java.sql.*;

public class TransactionPurchaseDao {

    public void transaction(
            String purchaseId,
            String productId,
            String customerId,
            String purchaseDetailId,
            Integer quantity
    ) throws SQLException {
        Connection connection = DBConnector.getConnection();
        connection.setAutoCommit(false);

        String purchaseQuery = "INSERT INTO trx_purchase (id, purchase_date, customer_id) VALUES (?, ?, ?)";
        PreparedStatement trxPurchaseStatement = connection.prepareStatement(purchaseQuery);
        trxPurchaseStatement.setString(1, customerId);
        trxPurchaseStatement.setDate(2, new Date(0));
        trxPurchaseStatement.setString(3, customerId);
        trxPurchaseStatement.execute();
        trxPurchaseStatement.close();

        String getProductQuery = "SELECT * FROM product WHERE id = ?";
        PreparedStatement getProductPreparedStatement = connection.prepareStatement(getProductQuery);
        getProductPreparedStatement.setString(1, productId);

        ResultSet productResultSet = getProductPreparedStatement.executeQuery();
        int productPrice;
        int productStock;

        if (productResultSet.next()) {
            productPrice = productResultSet.getInt("product_price");
            productStock = productResultSet.getInt("stock");
        } else {
            connection.rollback();
            throw new RuntimeException("Product with id" + productId + "not found");
        }
        getProductPreparedStatement.close();

        String purchaseDetailQuery = "INSERT INTO trx_purchase_detail " +
                "(id, product_price, quantity, total_price, product_id, purchase_id) VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement purchaseDetailPreparedStatement = connection.prepareStatement(purchaseDetailQuery);
        purchaseDetailPreparedStatement.setString(1, purchaseDetailId);
        purchaseDetailPreparedStatement.setInt(2, productPrice);
        purchaseDetailPreparedStatement.setInt(3, quantity);
        purchaseDetailPreparedStatement.setInt(4, productPrice * quantity);
        purchaseDetailPreparedStatement.setString(5, productId);
        purchaseDetailPreparedStatement.setString(6, purchaseId);
        purchaseDetailPreparedStatement.execute();
        purchaseDetailPreparedStatement.close();

        String updateProductQuery = "UPDATE product SET stock = ? WHERE id = ?";
        PreparedStatement updateProductPreparedStatement = connection.prepareStatement(updateProductQuery);
        updateProductPreparedStatement.setInt(1, productStock - quantity);
        updateProductPreparedStatement.setString(2, productId);
        updateProductPreparedStatement.execute();
        updateProductPreparedStatement.close();

        connection.commit();
        connection.close();
    }

}