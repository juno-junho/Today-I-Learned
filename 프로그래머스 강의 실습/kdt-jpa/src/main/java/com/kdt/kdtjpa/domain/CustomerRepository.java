package com.kdt.kdtjpa.domain;

import com.kdt.kdtjpa.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
