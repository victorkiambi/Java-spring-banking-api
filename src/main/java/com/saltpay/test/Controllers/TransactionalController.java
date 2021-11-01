package com.saltpay.test.Controllers;

import com.saltpay.test.DTO.AccountTransactionDTO;
import com.saltpay.test.DTO.TransactionDTO;
import com.saltpay.test.models.Account;
import com.saltpay.test.response.ResponseHandler;
import com.saltpay.test.services.TransactionService;
import org.springframework.http.HttpEntity;
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
    public HttpEntity<? extends Object> getTransactions(@PathVariable Long accNo) {
        List<TransactionDTO> transactionDTO = transactionService.findTransactionsByAccount(accNo);
        if (transactionDTO == null) {
            return ResponseHandler.generateResponse("No account found for the Account Number", HttpStatus.NOT_FOUND, null);
        }
        else {
            return ResponseHandler.generateResponse("Success", HttpStatus.OK,transactionDTO);
        }
    }

    /*
    Deposit to own account
    */
    @PostMapping("/api/v1/transaction/deposit")
    public ResponseEntity<Object> depositToOwnAccount(@Valid @RequestBody Account newAccount)  {
        AccountTransactionDTO transaction = transactionService.depositToOwnAccount(newAccount);
        return getAccountTransactionDTO(transaction);
    }

    /*
    Transfer to different account
    */
    @PostMapping("/api/v1/transaction/transfer")
    public ResponseEntity<Object> accountToAccountTransfer(@Valid @RequestBody Account newTransaction) {
        AccountTransactionDTO transaction = transactionService.accountToAccountTransfer(newTransaction);
        return getAccountTransactionDTO(transaction);
    }

    private ResponseEntity<Object> getAccountTransactionDTO(AccountTransactionDTO transaction) {
        if (transaction == null) {
            return ResponseHandler.generateResponse("No account found", HttpStatus.NOT_FOUND, null);
        }
//        if(transaction.getTransactionDetails().equals("Insufficient funds")) {
//            return ResponseHandler.generateResponse("Sender has insufficient funds for this request", HttpStatus.BAD_REQUEST,null);
//        }
        else {
            return ResponseHandler.generateResponse("Success", HttpStatus.CREATED,transaction);
        }
    }
}
