package com.saltpay.test.DTO;

import com.saltpay.test.models.Account;
import lombok.Data;

import java.util.List;

@Data
public class CustomerDTO {
    private Long customer_id;

    private String name;
    private String email;
    private Integer phone;
    private List<Account> accounts;
}

