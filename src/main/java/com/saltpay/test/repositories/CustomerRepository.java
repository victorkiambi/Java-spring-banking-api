package com.saltpay.test.repositories;

import com.saltpay.test.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    @Override
    List<Customer> findAll();

    Optional<Customer> findByCustomerId(Long id);

}
