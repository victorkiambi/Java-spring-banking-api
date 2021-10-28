package com.saltpay.test.DTO;

import com.saltpay.test.models.Account;
import com.saltpay.test.models.Customer;
import com.saltpay.test.models.Transaction;
import lombok.Data;

import java.util.List;
import java.util.Optional;

@Data
public class AccountDTO {

    private  Long accId;
    private String accName;
    private String accBranch;
    private String name;
    private String email;
    private List<Transaction> transaction;

    public AccountDTO(Optional<Account> account) {
    }

    public AccountDTO() {

    }
}
