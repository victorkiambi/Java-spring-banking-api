package com.saltpay.test.services;

import com.saltpay.test.DTO.AccountDTO;
import com.saltpay.test.models.Account;

public interface AccountService {

    AccountDTO saveAccount(Account account);


    AccountDTO getAccount(Long accId);
}
