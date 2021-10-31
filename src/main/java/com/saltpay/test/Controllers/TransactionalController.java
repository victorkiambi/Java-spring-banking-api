package com.saltpay.test.Controllers;

import com.saltpay.test.DTO.TransactionDTO;
import com.saltpay.test.models.Account;
import com.saltpay.test.models.Transaction;
import com.saltpay.test.services.TransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.rmi.ServerException;
import java.util.List;

@RestController
public class TransactionalController {

    private final TransactionService transactionService;
    public TransactionalController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    /*
    Get Transaction Details via accountId
     */
    @GetMapping("/api/v1/transactions/{accNo}")
    public List<TransactionDTO> getTransactions(@PathVariable Long accNo) {
        return transactionService.findTransactionsByAccount(accNo);
    }

    /*
    Deposit to own account
    */
    @PostMapping("/api/v1/transaction/deposit")
    public ResponseEntity<Account> createDeposit(@Valid @RequestBody Transaction newTransaction)  {
        Account transaction = transactionService.createTransaction(newTransaction);
        if (transaction == null) {
            return ResponseEntity.noContent().build();
        } else {

            return new ResponseEntity<>(transaction, HttpStatus.CREATED);
        }
    }

    /*
    Transfer to different account
    */
    @PostMapping("/api/v1/transaction/transfer")
    public Account createTransfer(@Valid @RequestBody Transaction newTransaction) throws ServerException {
        return transactionService.bankTransfer(newTransaction);
    }
}
