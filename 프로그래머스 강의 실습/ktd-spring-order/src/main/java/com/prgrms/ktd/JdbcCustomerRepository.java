package com.prgrms.ktd;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.UUID;

public class JdbcCustomerRepository {

    private static final Logger logger = LoggerFactory.getLogger(JdbcCustomerRepository.class);

    public static void main(String[] args) {

        try (
                var connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/order_mgmt", "root", "sean5633");
                var statement = connection.createStatement();
                var resultSet = statement.executeQuery("select * from customers");
        ) {
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                UUID customerId = UUID.nameUUIDFromBytes(resultSet.getBytes("customer_id"));
                logger.info("customer id -> {}, name -> {}", customerId, name);
            }
        } catch (SQLException throwables) {
            logger.error("Got error while closing connection", throwables);
        }
    }
}
