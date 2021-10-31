package com.saltpay.test.DTO;

import com.saltpay.test.models.Account;
import com.saltpay.test.models.Customer;
import lombok.Data;

import java.util.List;

@Data
public class CustomerDTO {
    private Long customerId;

    private String customerName;

    private String customerEmail;

    private Integer customerPhone;
    private List<Account> accounts;

    public CustomerDTO(Long customerId, String customerName, String customerEmail, Integer customerPhone) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.customerPhone = customerPhone;
    }

    public CustomerDTO() {

    }
}

