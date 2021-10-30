package com.saltpay.test.services;

import com.saltpay.test.DTO.AccountDTO;
import com.saltpay.test.models.Account;

import java.util.List;

public interface AccountService {

    Account saveAccount(Account account);


    List<AccountDTO> getAccount(Long accId);
}
