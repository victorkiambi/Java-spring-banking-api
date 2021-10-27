package com.saltpay.test.DTO;

import com.saltpay.test.models.Customer;
import com.saltpay.test.models.Transaction;

import java.util.List;

public class AccountDTO {

    private  Long acc_id;
    private String acc_name;
    private String acc_branch;
    private Customer customer;
    private List<Transaction> transaction;
}
