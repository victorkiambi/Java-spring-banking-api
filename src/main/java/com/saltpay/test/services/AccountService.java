package com.saltpay.test.services;

import com.saltpay.test.DTO.AccountDTO;
import com.saltpay.test.models.Account;

public interface AccountService {

    /**
     * Handles account creation
     * @param account
     * @return
     */
    AccountDTO saveAccount(Account account);

    /**
     * Finds account details by account no.
     * @param accNo
     * @return
     */

    AccountDTO getAccount(Long accNo);
}
