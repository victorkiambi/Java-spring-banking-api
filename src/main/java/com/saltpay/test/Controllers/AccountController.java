package com.saltpay.test.Controllers;

import com.saltpay.test.DTO.AccountDTO;
import com.saltpay.test.models.Account;
import com.saltpay.test.services.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.rmi.ServerException;
import java.util.List;

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
    public AccountDTO getAccount(@PathVariable Long accNo){
        return accountService.getAccount(accNo);

    }

    /*
    Create new account details
     */
    @PostMapping("/api/v1/accounts")
    public ResponseEntity<Account> create(@Valid @RequestBody Account newAccount) {
        Account account = accountService.saveAccount(newAccount);
        if (account == null) {
            return ResponseEntity.noContent().build();
        } else {

            return new ResponseEntity<>(account, HttpStatus.CREATED);
        }
    }
}
