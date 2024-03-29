package com.prgrms.ktd.customer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.nio.ByteBuffer;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@Primary
public class CustomerJdbcRepository implements CustomerRepository {

    private static final Logger logger = LoggerFactory.getLogger(CustomerJdbcRepository.class);

    private final DataSource dataSource;

    private final JdbcTemplate jdbcTemplate; // jdbc template는 datasource가 필요 없다.

    private static final RowMapper<Customer> customerRowMapper = (resultSet, rowNum) -> {
        String customerName = resultSet.getString("name");
        String email = resultSet.getString("email");
        UUID customerId = toUUID(resultSet.getBytes("customer_id"));
        LocalDateTime lastLoginAt = resultSet.getTimestamp("last_login_at") != null ?
                resultSet.getTimestamp("last_login_at").toLocalDateTime() : null;
        LocalDateTime createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();
        return new Customer(customerId, customerName, email, lastLoginAt, createdAt);
    };

    public CustomerJdbcRepository(DataSource dataSource, JdbcTemplate jdbcTemplate) {
        this.dataSource = dataSource;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Customer insert(Customer customer) {
        try (
                var connection = dataSource.getConnection();
                var statement = connection.prepareStatement("INSERT INTO customers(customer_id, name, email, created_at) VALUES (UUID_TO_BIN(?),?,?,?)")
        ) {
            statement.setBytes(1, customer.getCustomerId().toString().getBytes());
            statement.setString(2, customer.getName());
            statement.setString(3, customer.getEmail());
            statement.setTimestamp(4, Timestamp.valueOf(customer.getCreatedAt()));
            int executeUpdate = statement.executeUpdate();
            if (executeUpdate != 1) {
                throw new RuntimeException("Nothing was inserted");
            }
            return customer;
        } catch (SQLException throwables) {
            logger.error("Got error while closing connection", throwables);
            throw new RuntimeException(throwables);
        }
    }

    @Override
    public Customer update(Customer customer) {
        try (
                var connection = dataSource.getConnection();
                var statement = connection.prepareStatement("UPDATE customers SET name = ?, email = ?, last_login_at = ? WHERE customer_id = UUID_TO_BIN(?)")
        ) {
            statement.setString(1, customer.getName());
            statement.setString(2, customer.getEmail());
            statement.setTimestamp(3, customer.getLastLoginAt() != null ? Timestamp.valueOf(customer.getLastLoginAt()) : null);
            statement.setBytes(4, customer.getCustomerId().toString().getBytes());
            int executeUpdate = statement.executeUpdate();
            if (executeUpdate != 1) {
                throw new RuntimeException("Nothing was updated");
            }
            return customer;
        } catch (SQLException e) {
            logger.error("Got error while closing connection", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public int count() {
        return jdbcTemplate.queryForObject("select count(*) from customers", Integer.class);
    }

    @Override
    public List<Customer> findAll() {
        return jdbcTemplate.query("select * from customers", customerRowMapper);

        /*List<Customer> allCustomers = new ArrayList<>();

        try (
                var connection = dataSource.getConnection(); // 관심사의 분리가 정확히 적용됨
                var statement = connection.prepareStatement("select * from customers");
                var resultSet = statement.executeQuery()
        ) {
            while (resultSet.next()) {
                mapToCustomer(allCustomers, resultSet);
            }
        } catch (SQLException throwable) {
            logger.error("Got error while closing connection", throwable);
            throw new RuntimeException(throwable);
        }
        return allCustomers;*/
    }

    private Customer mapToCustomer(ResultSet resultSet) throws SQLException {
        String customerName = resultSet.getString("name");
        String email = resultSet.getString("email");
        UUID customerId = toUUID(resultSet.getBytes("customer_id"));
        LocalDateTime lastLoginAt = resultSet.getTimestamp("last_login_at") != null ?
                resultSet.getTimestamp("last_login_at").toLocalDateTime() : null;
        LocalDateTime createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();
        return new Customer(customerId, customerName, email, lastLoginAt, createdAt);
    }

    private static void mapToCustomer(List<Customer> allCustomers, ResultSet resultSet) throws SQLException {
        String customerName = resultSet.getString("name");
        String email = resultSet.getString("email");
        UUID customerId = toUUID(resultSet.getBytes("customer_id"));
        LocalDateTime lastLoginAt = resultSet.getTimestamp("last_login_at") != null ?
                resultSet.getTimestamp("last_login_at").toLocalDateTime() : null;
        LocalDateTime createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();
        logger.info("customer id -> {}, name -> {}, createdAt -> {}", customerId, customerName, createdAt);
        allCustomers.add(new Customer(customerId, customerName, email, lastLoginAt, createdAt));
    }

    @Override
    public Optional<Customer> findById(UUID customerId) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject("select * from customers where customer_id = UUID_TO_BIN(?)",
                    customerRowMapper,
                    customerId.toString().getBytes()));
        } catch (EmptyResultDataAccessException e) {
            logger.error("Got empty result", e);
            return Optional.empty();
        }

//        List<Customer> allCustomers = new ArrayList<>();

      /*  try (
                var connection = dataSource.getConnection();
                var statement = connection.prepareStatement("select * from customers where customer_id = UUID_TO_BIN(?)")
        ) {
            statement.setBytes(1, customerId.toString().getBytes());
            logger.info("statement -> {}", statement);
            try (var resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    mapToCustomer(allCustomers, resultSet);
                }
            }
        } catch (SQLException e) {
            logger.error("Got error while closing connection", e);
            throw new RuntimeException(e);
        }
        return allCustomers.stream().findFirst();*/
    }

    @Override
    public Optional<Customer> findByName(String name) {
        List<Customer> names = new ArrayList<>();

        try (
                var connection = dataSource.getConnection();
                var statement = connection.prepareStatement("select * from customers where name = ?")
        ) {
            statement.setString(1, name);
            logger.info("statement -> {}", statement);
            try (var resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    mapToCustomer(names, resultSet);
                }
            }
        } catch (SQLException e) {
            logger.error("Got error while closing connection", e);
            throw new RuntimeException(e);
        }
        return names.stream().findFirst();
    }

    @Override
    public Optional<Customer> findByEmail(String email) {
        List<Customer> allCustomers = new ArrayList<>();

        try (
                var connection = dataSource.getConnection();
                var statement = connection.prepareStatement("select * from customers where email = ?")
        ) {
            statement.setString(1, email);
            logger.info("statement -> {}", statement);
            try (var resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    mapToCustomer(allCustomers, resultSet);
                }
            }
        } catch (SQLException e) {
            logger.error("Got error while closing connection", e);
            throw new RuntimeException(e);
        }
        return allCustomers.stream().findFirst();
    }

    @Override
    public void deleteAll() {
        try (
                var connection = dataSource.getConnection();
                var statement = connection.prepareStatement("DELETE FROM customers")
        ) {
            statement.executeUpdate();
        } catch (SQLException throwable) {
            logger.error("Got error while closing connection", throwable);
            throw new RuntimeException(throwable);
        }
    }

    static UUID toUUID(byte[] bytes) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }
}
