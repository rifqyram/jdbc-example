package com.enigma.jdbc;

import com.enigma.jdbc.product.ProductDao;
import com.enigma.jdbc.transaction.TransactionPurchaseDao;
import com.enigma.jdbc.user.UserDao;

import java.sql.SQLException;

public class Main {

    public static void main(String[] args) {

        UserDao userDao = new UserDao();
        ProductDao productDao = new ProductDao();
        TransactionPurchaseDao transactionPurchaseDao = new TransactionPurchaseDao();

        try {
            transactionPurchaseDao.transaction(
                    "1",
                    "ff8081817e470b96017e470e630b0001",
                    "1",
                    "1",
                    5
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
