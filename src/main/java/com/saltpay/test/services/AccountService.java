package com.saltpay.test.services;

import com.saltpay.test.DTO.AccountDTO;
import com.saltpay.test.models.Account;

public interface AccountService {

    Object saveAccount(Account account);

    AccountDTO getAccountByAccId(Long accId);
}
