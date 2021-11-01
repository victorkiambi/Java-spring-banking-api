package com.saltpay.test.services;

import com.saltpay.test.DTO.AccountTransactionDTO;
import com.saltpay.test.DTO.TransactionDTO;
import com.saltpay.test.models.Account;

import java.util.List;

public interface TransactionService {

    /**
     * Finds all transactions based on given account number
     * @param accNo
     * @return all tansactions
     */
    List<TransactionDTO> findTransactionsByAccount(Long accNo);

    /**
     * Handles deposit to own personal account
     * @param transaction
     * @return new account balance details
     */
    AccountTransactionDTO depositToOwnAccount(Account transaction);

    /**
     * Handles account to account transfers
     * @param newTransaction
     * @return new account balance details
     */
    AccountTransactionDTO accountToAccountTransfer(Account newTransaction);
}
