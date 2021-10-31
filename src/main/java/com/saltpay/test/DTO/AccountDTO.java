package com.saltpay.test.DTO;

import com.saltpay.test.models.Customer;
import com.saltpay.test.models.Transaction;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class AccountDTO {

    private ResponseDTO responseDTO;
    private Long accNo;
    private String accName;
    private String accBranch;
    private double minBalance;
    private String name;
    private Integer phone;
    private String email;

    private List<Transaction> transactions;



}
