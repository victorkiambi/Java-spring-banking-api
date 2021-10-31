package com.saltpay.test.Controllers;

import com.saltpay.test.DTO.CustomerDTO;
import com.saltpay.test.models.Customer;
import com.saltpay.test.services.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class CustomerController {


    private final CustomerService customerService;

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
    public CustomerDTO getCustomers(@PathVariable Long customerId) {
        return customerService.getCustomerById(customerId);
    }

    /*
    Create customer
     */
    @PostMapping(path = "/api/v1/customers",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Customer> create(@Valid @RequestBody Customer newCustomer) {
        Customer customer = customerService.save(newCustomer);
        return new ResponseEntity<>(customer, HttpStatus.CREATED);
    }
}
