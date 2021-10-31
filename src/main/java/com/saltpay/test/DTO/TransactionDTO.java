package com.saltpay.test.DTO;

import com.saltpay.test.models.Account;
import com.saltpay.test.models.TransactionType;
import lombok.Data;

import java.util.UUID;

@Data
public class TransactionDTO {
    private UUID transactionReferenceNo;
    private double transactionAmount;
    private TransactionType transactionType;

    public TransactionDTO(UUID transactionReferenceNo, double transactionAmount, TransactionType transactionType) {
        this.transactionReferenceNo= transactionReferenceNo;
        this.transactionAmount = transactionAmount;
        this.transactionType = transactionType;
    }

    public TransactionDTO() {
    }
}
