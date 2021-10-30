package com.saltpay.test.repositories;

import com.saltpay.test.models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findTransactionByAccount_AccNo(Long accNo);
}
