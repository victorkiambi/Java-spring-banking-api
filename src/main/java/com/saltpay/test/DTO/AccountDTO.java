package com.saltpay.test.DTO;

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


    public AccountDTO(Long accNo, String accName, String accBranch, double minBalance, String name, Integer phone, String email) {
        this.accNo = accNo;
        this.accName = accName;
        this.accBranch = accBranch;
        this.minBalance = minBalance;
        this.name = name;
        this.phone = phone;
        this.email = email;
    }

    public AccountDTO(String accName, String accBranch, double minBalance, Customer customer) {
        this.accName = accName;
        this.accBranch = accBranch;
        this.minBalance = minBalance;
    }
    //    private List<Transaction> transaction;



}
