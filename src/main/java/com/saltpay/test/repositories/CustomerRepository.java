package com.saltpay.test.repositories;

import com.saltpay.test.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    @Override
    List<Customer> findAll();


}
