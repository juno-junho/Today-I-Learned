package com.prgrms.ktd;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.UUID;

public class JdbcCustomerRepository {

    private static final Logger logger = LoggerFactory.getLogger(JdbcCustomerRepository.class);

    public static void main(String[] args) {

        Connection connection = null; // 예외 발생 시 꼭 닫아줘야 하기 때문에
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/order_mgmt", "root", "sean5633");
            statement = connection.createStatement();
            resultSet = statement.executeQuery("select * from customers");
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                UUID customerId = UUID.nameUUIDFromBytes(resultSet.getBytes("customer_id"));
                logger.info("customer id -> {}, name -> {}", customerId, name);
            }
        } catch (SQLException throwables) {
            logger.error("Got error while closing connection", throwables);
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
                if (statement != null) {
                    statement.close();
                }
                if (resultSet != null) {
                    resultSet.close();
                }
            } catch (SQLException exception) {
                logger.error("Got error while closing connection", exception);
            }
        }
    }
}
