package com.saltpay.test.services;

import com.saltpay.test.DTO.TransactionDTO;
import com.saltpay.test.models.Account;
import com.saltpay.test.models.Transaction;

import java.util.List;

public interface TransactionService {

    List<TransactionDTO> findTransactionsByAccount(Long accId);

    Account createTransaction(Transaction transaction);
}
