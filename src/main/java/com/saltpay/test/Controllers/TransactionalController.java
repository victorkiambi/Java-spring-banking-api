package com.saltpay.test.Controllers;

import com.saltpay.test.DTO.AccountTransactionDTO;
import com.saltpay.test.DTO.TransactionDTO;
import com.saltpay.test.models.Account;
import com.saltpay.test.models.Transaction;
import com.saltpay.test.response.ResponseHandler;
import com.saltpay.test.services.TransactionService;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Handles incoming transaction requests
 * /api/v1/transactions/{accNo}
 * /api/v1/transaction/deposit
 * /api/v1/transaction/transfer
 */
@RestController
public class TransactionalController {


    private final TransactionService transactionService;

    /**
     *
     * @param transactionService
     */
    public TransactionalController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    /**
     * Get all transactions for single account via Account No.
     * @param accNo
     * @return transactions
     */
    @GetMapping("/api/v1/transactions/{accNo}")
    public HttpEntity<? extends Object> getTransactions(@PathVariable Long accNo) {
        List<TransactionDTO> transactionDTO = transactionService.findTransactionsByAccount(accNo);
        if (transactionDTO == null) {
            return ResponseHandler.generateResponse("No account found for the Account Number", HttpStatus.NOT_FOUND, null);
        }
        else {
            return ResponseHandler.generateResponse("Success", HttpStatus.OK,transactionDTO);
        }
    }

    /**
     * Handles deposit to own account
     * @param newTransaction
     * @return new account balance
     */
    @PostMapping("/api/v1/transaction/deposit")
    public ResponseEntity<Object> depositToOwnAccount(@Valid @RequestBody Transaction newTransaction)  {
        AccountTransactionDTO transaction = transactionService.depositToOwnAccount(newTransaction);
        return getAccountTransactionDTO(transaction);
    }

    /**
     * Handles account to account transfer
     * @param newTransaction
     * @return successful transaction details
     */
    @PostMapping("/api/v1/transaction/transfer")
    public ResponseEntity<Object> accountToAccountTransfer(@Valid @RequestBody Transaction newTransaction) {
        AccountTransactionDTO transaction = transactionService.accountToAccountTransfer(newTransaction);
        return getAccountTransactionDTO(transaction);
    }

    /**
     * method to handle responses
     * @param transaction
     * @return
     */

    private ResponseEntity<Object> getAccountTransactionDTO(AccountTransactionDTO transaction) {
        if (transaction == null) {
            return ResponseHandler.generateResponse("No account found", HttpStatus.NOT_FOUND, null);
        }

        else {
            return ResponseHandler.generateResponse("Success", HttpStatus.CREATED,transaction);
        }
    }
}
