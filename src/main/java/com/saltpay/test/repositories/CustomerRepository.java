package com.saltpay.test.repositories;

import com.saltpay.test.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    @Override
    List<Customer> findAll();

//    @Query("select c from Customer c where c.customerId = :id")
    List<Customer> findByCustomerId(Long customerId);

//    Optional<Customer> findCustomerByCustomerId(Long id);

}
