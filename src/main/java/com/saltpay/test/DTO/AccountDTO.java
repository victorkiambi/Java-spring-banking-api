package com.saltpay.test.DTO;

import com.saltpay.test.models.Transaction;
import lombok.Data;

import java.util.List;

@Data
public class AccountDTO {

    private Long accId;
    private String accName;
    private String accBranch;
    private double actualBalance;
    private String name;
    private Integer phone;
    private String email;
//    private List<Transaction> transaction;


}
