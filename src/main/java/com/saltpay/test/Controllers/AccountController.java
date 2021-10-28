package com.saltpay.test.Controllers;

import com.saltpay.test.DTO.AccountDTO;
import com.saltpay.test.models.Account;
import com.saltpay.test.services.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.rmi.ServerException;

@RestController
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/api/v1/accounts/{accId}")
    public AccountDTO getAccount(@PathVariable Long accId){
        return accountService.getAccountByAccId(accId);

    }

    @PostMapping("/api/v1/accounts")
    public ResponseEntity<Account> create(@Valid @RequestBody Account newAccount) throws ServerException {
        Account account = (Account) accountService.saveAccount(newAccount);
        if (account == null) {
            throw new ServerException("No customer Found");
        } else {
            return new ResponseEntity<>(account, HttpStatus.CREATED);
        }
    }
}
