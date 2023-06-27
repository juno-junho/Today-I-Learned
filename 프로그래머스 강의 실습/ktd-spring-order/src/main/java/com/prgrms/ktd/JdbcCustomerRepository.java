package com.prgrms.ktd;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class JdbcCustomerRepository {

    private static final Logger logger = LoggerFactory.getLogger(JdbcCustomerRepository.class);

    public List<String> findNames(String name) {
        String SELECT_SQL = "select * from customers WHERE name = '%s'".formatted(name);
        logger.info(SELECT_SQL);
        List<String> names = new ArrayList<>();

        try (
                var connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/order_mgmt", "root", "sean5633");
                var statement = connection.createStatement();
                var resultSet = statement.executeQuery(SELECT_SQL)
        ) {
            while (resultSet.next()) {
                String customerName = resultSet.getString("name");
                UUID customerId = UUID.nameUUIDFromBytes(resultSet.getBytes("customer_id"));
                LocalDateTime createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();
                logger.info("customer id -> {}, name -> {}, createdAt -> {}", customerId, customerName, createdAt);
                names.add(customerName);
            }
        } catch (SQLException throwables) {
            logger.error("Got error while closing connection", throwables);
        }
        return names;
    }

    public static void main(String[] args) {

        List<String> names = new JdbcCustomerRepository().findNames("tester01' OR 'a'= 'a"); // SQL Injection
        names.forEach(v -> logger.info("Found name : {}", v));

    }
}
