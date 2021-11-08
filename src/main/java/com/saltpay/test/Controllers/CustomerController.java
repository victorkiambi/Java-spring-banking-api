package com.saltpay.test.Controllers;

import com.saltpay.test.DTO.CustomerDTO;
import com.saltpay.test.models.Customer;
import com.saltpay.test.response.ResponseHandler;
import com.saltpay.test.services.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Handles incoming customer requests
 * /api/v1/customers
 * /api/v1/cutomer/{customerId}
 */
@RestController
public class CustomerController {


    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    /**
     * Get all customers
     * @return customers with account details
     */
    @GetMapping("/api/v1/customers")
    @ResponseBody
    public ResponseEntity<Object> getAllCustomers() {
        return ResponseHandler.generateResponse("Success", HttpStatus.OK, customerService.getAllCustomers());
    }

    /**
     * Get customer details via customer Id
     *
     * @param customerId
     * @return single customer details
     */
    @GetMapping("/api/v1/customer/{customerId}")
    @ResponseBody
    public ResponseEntity<Object> getCustomers(@PathVariable Long customerId) {
        CustomerDTO customerDTO = customerService.getCustomerById(customerId);
        if (customerDTO == null){
            return ResponseHandler.generateResponse("No Customer Found", HttpStatus.NOT_FOUND, null );
        }
        else {
            return ResponseHandler.generateResponse("Success", HttpStatus.OK, customerDTO );

        }
    }

    /**
     * Create a customer
     * @param newCustomer
     * @return new customer created
     */
    @PostMapping(path = "/api/v1/customers",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> create(@Valid @RequestBody Customer newCustomer) {
        Customer customer = customerService.save(newCustomer);
        return ResponseHandler.generateResponse("Success", HttpStatus.CREATED, customer);
    }
}
