package com.kdt.kdtjpa;

import com.kdt.kdtjpa.domain.CustomerEntity;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@SpringBootTest
public class JPATest {

    @Autowired
    CustomerRepository repository;

    @BeforeEach
    void setUp() {

    }

    @AfterEach
    void tearDown() {
        repository.deleteAll();
    }

    @Test
    void insert_test() {
        // Given
        CustomerEntity customer = new CustomerEntity();
        customer.setId(1L);
        customer.setFirstName("junho");
        customer.setLastName("hwang");

        // When
        repository.save(customer);

        // Then
        CustomerEntity entity = repository.findById(1L).get();
    }

    @Test
    @Transactional
    void update_test() {
        // Given
        CustomerEntity customer = new CustomerEntity();
        customer.setId(1L);
        customer.setFirstName("junho");
        customer.setLastName("hwang");
        repository.save(customer);

        // When
        CustomerEntity entity = repository.findById(1L).get();
        entity.setFirstName("backend");
        entity.setLastName("programmers");

        // Then
        CustomerEntity updated = repository.findById(1L).get();
    }
}
