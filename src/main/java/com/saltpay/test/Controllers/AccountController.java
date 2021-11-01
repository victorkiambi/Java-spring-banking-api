package com.saltpay.test.Controllers;

import com.saltpay.test.DTO.AccountDTO;
import com.saltpay.test.models.Account;
import com.saltpay.test.response.ResponseHandler;
import com.saltpay.test.services.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Handles incoming account requests
 * /api/v1/accounts
 */

@RestController
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    /*
    Get Account Details via accountId
     */
    @GetMapping("/api/v1/accounts/{accNo}")
    public ResponseEntity<Object> getAccount(@PathVariable Long accNo){
        AccountDTO accountDTO = accountService.getAccount(accNo);
        if (accountDTO == null){
            return ResponseHandler.generateResponse("No account found for the account number", HttpStatus.NOT_FOUND,null);
        }
        return ResponseHandler.generateResponse("Success", HttpStatus.OK,accountDTO);
    }

    /*
    Create new account
     */
    @PostMapping("/api/v1/accounts")
    public ResponseEntity<Object> create(@Valid @RequestBody Account newAccount) {
        AccountDTO account = accountService.saveAccount(newAccount);
        if (account == null) {
            return ResponseHandler.generateResponse("No Customer found for given Customer Id", HttpStatus.NOT_FOUND,404);
        } else {
            return ResponseHandler.generateResponse("Success", HttpStatus.CREATED, account);
        }
    }
}
