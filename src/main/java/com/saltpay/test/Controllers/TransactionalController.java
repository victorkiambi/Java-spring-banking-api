package com.saltpay.test.Controllers;

import com.saltpay.test.DTO.AccountTransactionDTO;
import com.saltpay.test.DTO.TransactionDTO;
import com.saltpay.test.models.Account;
import com.saltpay.test.services.TransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    public ResponseEntity<AccountTransactionDTO> depositToOwnAccount(@Valid @RequestBody Account newAccount)  {
        AccountTransactionDTO transaction = transactionService.depositToOwnAccount(newAccount);
        return getAccountTransactionDTO(transaction);
    }

    /*
    Transfer to different account
    */
    @PostMapping("/api/v1/transaction/transfer")
    public ResponseEntity<AccountTransactionDTO> accountToAccountTransfer(@Valid @RequestBody Account newTransaction) {
        AccountTransactionDTO transaction = transactionService.accountToAccountTransfer(newTransaction);
        return getAccountTransactionDTO(transaction);
    }

    private ResponseEntity<AccountTransactionDTO> getAccountTransactionDTO(AccountTransactionDTO transaction) {
        if (transaction.getResponse().getResponseCode() == 404) {
            return new ResponseEntity<>(transaction, HttpStatus.NOT_FOUND);
        }
        if(transaction.getResponse().getResponseCode() == 400) {
            return new ResponseEntity<>(transaction, HttpStatus.BAD_REQUEST);
        }else {
            return new ResponseEntity<>(transaction, HttpStatus.CREATED);
        }
    }
}
