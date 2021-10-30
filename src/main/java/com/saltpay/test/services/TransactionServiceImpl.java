package com.saltpay.test.services;

import com.saltpay.test.DTO.TransactionDTO;
import com.saltpay.test.models.Account;
import com.saltpay.test.models.Transaction;
import com.saltpay.test.models.TransactionType;
import com.saltpay.test.repositories.AccountRepository;
import com.saltpay.test.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TransactionServiceImpl implements TransactionService{

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public List<TransactionDTO> findTransactionsByAccount(Long accNo) {

        List<Transaction> transactionDTOS = transactionRepository.findTransactionByAccount_AccNo(accNo);
        return transactionDTOS.
                stream().
                map(this::convertToTransactionDTO).collect(Collectors.toList());
    }

    @Override
    public Account createTransaction(Transaction transaction) {
        List<Account> accounts = accountRepository.findByAccNo(transaction.getSenderAccNo());

        if (accounts.isEmpty()){
            return null;
        }
        else{
            double minBalance = accounts.get(0).getMinBalance();
            double depositedAmount = transaction.getTransactionAmount();
            double newBalance = minBalance + depositedAmount;

            Account account = accountRepository.getAccountByAccNo(transaction.getSenderAccNo());
            account.setMinBalance(newBalance);

            Transaction transaction1 = new Transaction();
            transaction1.setTransactionType(TransactionType.DEPOSIT);
            transaction1.setTransactionAmount(transaction.getTransactionAmount());
            account.addTransaction(transaction);
            return accountRepository.save(account);

        }
    }

    /*
   Entity to DTO cnversion
    */
    private TransactionDTO convertToTransactionDTO(Transaction transaction) {

        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setTransactionReferenceNo(transaction.getTransactionReferenceNo());
        transactionDTO.setTransactionType(transaction.getTransactionType());
        transactionDTO.setTransactionAmount(transaction.getTransactionAmount());
        return transactionDTO;
    }

}
