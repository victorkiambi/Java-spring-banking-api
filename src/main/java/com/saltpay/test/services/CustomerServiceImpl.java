package com.saltpay.test.services;

import com.saltpay.test.DTO.CustomerDTO;
import com.saltpay.test.models.Account;
import com.saltpay.test.models.Customer;
import com.saltpay.test.repositories.AccountRepository;
import com.saltpay.test.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }
    /*
    Get all customers with their account details
     */

    @Override
    public List<CustomerDTO> getAllCustomers() {
        return customerRepository.findAll().
                stream().
                map(this::convertToCustomerDTO).
                collect(Collectors.toList());
    }

    /*
    Create new Customer
     */
    @Override
    @Transactional
    public Customer save(Customer customer) {
        customerRepository.save(customer);
        return customer;
    }

    /*
    Get customer via Id
     */
    @Override
    public List<CustomerDTO> getCustomerById(Long customerId) {
        List<CustomerDTO> customer =customerRepository.findByCustomerId(customerId).stream().
                map(this::convertToCustomerDTO).collect(Collectors.toList());
        if (customer.isEmpty()){
            return null;
        }
        else {
            return customer;
        }
    }

    /*
    Entity to DTO conversion
     */
    private CustomerDTO convertToCustomerDTO(Customer customer) {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setCustomerId(customer.getCustomerId());
        customerDTO.setCustomerName(customer.getCustomerName());
        customerDTO.setCustomerEmail(customer.getCustomerEmail());
        customerDTO.setCustomerPhone(customer.getCustomerPhone());
        customerDTO.setAccounts(customer.getAccounts());
        return customerDTO;
    }


}
