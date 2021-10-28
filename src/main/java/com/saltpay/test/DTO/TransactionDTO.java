package com.saltpay.test.DTO;

import com.saltpay.test.models.Account;
import com.saltpay.test.models.TransactionType;
import lombok.Data;

@Data
public class TransactionDTO {
    private Long transactionId;
    private double amount;
    private TransactionType transactionType;
    private Account account;
}
