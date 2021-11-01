package com.saltpay.test.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Transient;

@Data
@NoArgsConstructor
public class AccountTransactionDTO {

    private Long accNo;
    private String accName;
    private String accBranch;
    private double minBalance;

    @Transient
    private transient double transactionAmount;
    private transient Long receiverAccNo;



    public AccountTransactionDTO (Long accNo, double transactionAmount) {
        this.accNo = accNo;
        this.transactionAmount = transactionAmount;
    }
    public AccountTransactionDTO (Long accNo, double transactionAmount, Long receiverAccNo){
        this.accNo = accNo;
        this.transactionAmount = transactionAmount;
        this.receiverAccNo = receiverAccNo;
    }

}
