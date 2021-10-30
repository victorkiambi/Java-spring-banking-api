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
   Create new account Transaction
    */
    @PostMapping("/api/v1/transaction")
    public ResponseEntity<Account> create(@Valid @RequestBody Transaction newTransaction) throws ServerException {
        Account transaction = transactionService.createTransaction(newTransaction);
        if (transaction == null) {
            throw new ServerException("No customer Found");
        } else {

            return new ResponseEntity<>(transaction, HttpStatus.CREATED);
        }
    }
}
