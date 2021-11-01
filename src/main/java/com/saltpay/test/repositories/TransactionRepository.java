package com.saltpay.test.repositories;

import com.saltpay.test.models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    /**
     * Finds all transactions by account number
     * @param accNo Long
     * @return
     */
    List<Transaction> findTransactionByAccount_AccNo(Long accNo);
}
