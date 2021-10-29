package com.saltpay.test.services;

import com.saltpay.test.DTO.AccountDTO;
import com.saltpay.test.models.Account;
import com.saltpay.test.models.Customer;
import com.saltpay.test.repositories.AccountRepository;
import com.saltpay.test.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private final AccountRepository accountRepository;

    @Autowired
    private CustomerRepository customerRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public Account saveAccount(Account account) {
        List<Customer> customer = customerRepository.findByCustomerId(account.getCustomerId());
        if (customer.isEmpty()){
            return null;
        }
        else{
            Account account1 = new Account();
            account1.setAccBranch(account.getAccBranch());
            account1.setAccName(account.getAccName());
            account1.setCustomerId(account.getCustomerId());
            return accountRepository.save(account1);
        }

    }

    @Override
    public List<AccountDTO> getAccountByAccId(Long id) {
        return accountRepository.findByAccId(id).
                stream().map(this::convertToAccountDTO).collect(Collectors.toList());
    }

    private AccountDTO convertToAccountDTO(Account account) {
        Customer customer = new Customer();
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setName(customer.getName());
        accountDTO.setEmail(customer.getEmail());
        accountDTO.setAccName(account.getAccName());
        accountDTO.setAccBranch(account.getAccBranch());
        return accountDTO;
    }
}
