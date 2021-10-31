package com.saltpay.test.services;

import com.saltpay.test.DTO.CustomerDTO;
import com.saltpay.test.models.Customer;

import java.util.List;

public interface CustomerService {

    List<CustomerDTO> getAllCustomers();

    Customer save(Customer customer);

    CustomerDTO getCustomerById(Long customerId);
}
