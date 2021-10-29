package com.saltpay.test.Controllers;

import com.saltpay.test.DTO.CustomerDTO;
import com.saltpay.test.models.Account;
import com.saltpay.test.models.Customer;
import com.saltpay.test.repositories.AccountRepository;
import com.saltpay.test.repositories.CustomerRepository;
import com.saltpay.test.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.rmi.ServerException;
import java.util.List;

@RestController
public class CustomerController {


    private final CustomerService customerService;

    @Autowired
    private CustomerRepository customerRepository;


    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    /*
    Get all customers with account details
     */
    @GetMapping("/api/v1/customers")
    @ResponseBody
    public ResponseEntity<List<CustomerDTO>> getAllCustomers() {
        return new ResponseEntity<>(customerService.getAllCustomers(), HttpStatus.OK);
    }

    /*
    Get specific customer with customer id
     */
    @GetMapping("/api/v1/customer/{customerId}")
    @ResponseBody
    public List<CustomerDTO> getCustomers(@PathVariable Long customerId) {
        return customerService.getCustomerById(customerId);
    }

    /*
    Create customer
     */
    @PostMapping(path = "/api/v1/customers",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Customer> create(@Valid @RequestBody Customer newCustomer) throws ServerException {
        Customer customer = customerRepository.save(newCustomer);
        return new ResponseEntity<>(customer, HttpStatus.CREATED);
    }
}
