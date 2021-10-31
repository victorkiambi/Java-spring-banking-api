package com.saltpay.test.services;

import com.saltpay.test.DTO.AccountTransactionDTO;
import com.saltpay.test.DTO.TransactionDTO;
import com.saltpay.test.models.Account;

import java.util.List;

public interface TransactionService {

    List<TransactionDTO> findTransactionsByAccount(Long accId);

    AccountTransactionDTO depositToOwnAccount(Account transaction);

    AccountTransactionDTO accountToAccountTransfer(Account newTransaction);
}
