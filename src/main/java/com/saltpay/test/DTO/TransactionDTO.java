package com.saltpay.test.DTO;

import com.saltpay.test.models.Account;
import com.saltpay.test.models.TransactionType;

public class TransactionDTO {
    private Long transaction_id;
    private double amount;
    private TransactionType transactionType;
    private Account account;
}
