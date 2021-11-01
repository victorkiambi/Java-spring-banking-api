package com.saltpay.test.services;

import com.saltpay.test.DTO.CustomerDTO;
import com.saltpay.test.models.Customer;

import java.util.List;

public interface CustomerService {

    /**
     * List all customers
     * @return customers with respective accounts
     */
    List<CustomerDTO> getAllCustomers();

    /**
     * Creates new customer
     * @param customer
     * @return customer details
     */
    Customer save(Customer customer);

    /**
     * Retrieves customer via customer Id
     * @param customerId
     * @return single customer details
     */
    CustomerDTO getCustomerById(Long customerId);
}
