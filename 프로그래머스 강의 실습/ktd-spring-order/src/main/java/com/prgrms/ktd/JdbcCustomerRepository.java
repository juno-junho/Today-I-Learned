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
    private static final String SELECT_BY_NAME_SQL = "select * from customers WHERE name = ?";
    private static final String SELECT_ALL_SQL = "select * from customers";
    private static final String INSERT_SQL = "INSERT INTO customers(customer_id, name, email) VALUES (UUID_TO_BIN(?),?,?)";
    private final String DELETE_ALL_SQL = "DELETE FROM customers";
//    private final String UPDATE_BY_ID_SQL = "UPDATE customers SET name = ? WHERE customer_id = UUID";

    public List<String> findNames(String name) {
//        String SELECT_SQL = "select * from customers WHERE name = '%s'".formatted(name);
        List<String> names = new ArrayList<>();

        try (
                var connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/order_mgmt", "root", "sean5633");
                var statement = connection.prepareStatement(SELECT_BY_NAME_SQL)
        ) {
            statement.setString(1, name);
            try (var resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    String customerName = resultSet.getString("name");
                    UUID customerId = UUID.nameUUIDFromBytes(resultSet.getBytes("customer_id"));
                    LocalDateTime createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();
                    logger.info("customer id -> {}, name -> {}, createdAt -> {}", customerId, customerName, createdAt);
                    names.add(customerName);
                }
            }
        } catch (SQLException throwables) {
            logger.error("Got error while closing connection", throwables);
        }
        return names;
    }

    public List<String> findAllName() {
        List<String> names = new ArrayList<>();

        try (
                var connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/order_mgmt", "root", "sean5633");
                var statement = connection.prepareStatement(SELECT_ALL_SQL);
                var resultSet = statement.executeQuery()
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

    public int insertCustomer(UUID customerId, String name, String email) {
        try (
                var connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/order_mgmt", "root", "sean5633");
                var statement = connection.prepareStatement(INSERT_SQL)
        ) {
            statement.setBytes(1, customerId.toString().getBytes());
            statement.setString(2, name);
            statement.setString(3, email);
            return statement.executeUpdate();
        } catch (SQLException throwables) {
            logger.error("Got error while closing connection", throwables);
        }
        return 0;
    }

    public int deleteAllCustomers() {
        try (
                var connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/order_mgmt", "root", "sean5633");
                var statement = connection.prepareStatement(INSERT_SQL)
        ) {
            return statement.executeUpdate();
        } catch (SQLException throwable) {
            logger.error("Got error while closing connection", throwable);
        }
        return 0;
    }


    public static void main(String[] args) {
        JdbcCustomerRepository customerRepository = new JdbcCustomerRepository();
        customerRepository.insertCustomer(UUID.randomUUID(), "new-user", "new-user@gmail.com");
        List<String> names = customerRepository.findNames("tester01' OR 'a'= 'a"); // SQL Injection
        names.forEach(v -> logger.info("Found name : {}", v));

    }
}
