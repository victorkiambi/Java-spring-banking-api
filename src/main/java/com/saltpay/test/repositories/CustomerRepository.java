package com.saltpay.test.repositories;

import com.saltpay.test.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    /**
     * Find all customers
     * @return
     */
    List<Customer> findAll();

    /**
     * Find single cutomer via customer id
     * @param customerId
     * @return
     */

    Customer findByCustomerId(Long customerId);


}
