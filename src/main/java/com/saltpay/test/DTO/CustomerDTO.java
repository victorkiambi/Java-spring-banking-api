package com.saltpay.test.DTO;

import com.saltpay.test.models.Account;
import com.saltpay.test.models.Customer;
import lombok.Data;

import java.util.List;
import java.util.Optional;

@Data
public class CustomerDTO {
    private Long customerId;

    private String name;
    private String email;
    private Integer phone;
    private List<Account> accounts;

    public CustomerDTO(Optional<Customer> customer) {
    }

    public CustomerDTO() {

    }
}

