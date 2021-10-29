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

}

