package com.saltpay.test.services;

import com.saltpay.test.DTO.CustomerDTO;
import com.saltpay.test.models.Customer;
import com.saltpay.test.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {


    CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public List<CustomerDTO> getAllCustomers() {
        return customerRepository.findAll().
                stream().
                map(this::convertToCustomerDTO).
                collect(Collectors.toList());
    }

    @Override
    public Customer save(Customer customer) {
        return customerRepository.save(customer);
    }

    private CustomerDTO convertToCustomerDTO(Customer customer) {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setCustomer_id(customer.getCustomer_id());
        customerDTO.setName(customer.getName());
        customerDTO.setEmail(customer.getEmail());
        customerDTO.setPhone(customer.getPhone());

        System.out.println(customerDTO);
        return customerDTO;
    }


}