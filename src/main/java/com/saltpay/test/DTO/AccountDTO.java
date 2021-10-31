package com.saltpay.test.DTO;

import com.saltpay.test.models.Transaction;
import lombok.Data;

import java.util.List;

@Data
public class AccountDTO {

    private Long accNo;
    private String accName;
    private String accBranch;
    private double minBalance;
    private String name;
    private Integer phone;
    private String email;
//    private List<Transaction> transaction;


}
