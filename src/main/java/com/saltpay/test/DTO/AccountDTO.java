package com.saltpay.test.DTO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.saltpay.test.models.Customer;
import com.saltpay.test.models.Transaction;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class AccountDTO {

    private Long accNo;
    private String accName;
    private String accBranch;
    private double minBalance;
    private String name;
    private Integer phone;
    private String email;

    @JsonIgnore
    private Customer customer;

    public AccountDTO(String accName, String accBranch, double minBalance, Customer customer) {
        this.accName = accName;
        this.accBranch = accBranch;
        this.minBalance = minBalance;
        this.customer = customer;
    }

}
