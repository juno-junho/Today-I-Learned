package com.kdt.kdtjpa;

import com.kdt.kdtjpa.domain.Customer;
import com.kdt.kdtjpa.domain.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

//@Slf4j
@SpringBootTest
public class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository repository;

    @Test
    void name() {
        // Given
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setFirstName("junho");
        customer.setLastName("hwang");

        // When
        repository.save(customer);

        // Then
        Customer entity = repository.findById(1L).get();
//        log.info("{} {}", entity.getLastName(), entity.getFirstName());
    }
}
