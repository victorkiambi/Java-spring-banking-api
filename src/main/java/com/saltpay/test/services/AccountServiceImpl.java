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

    /*
    Create new account for customer

    Check if customer exists first
     */
    @Override
    public Account saveAccount(Account account) {

        //check if customer exists
        List<Customer> customer = customerRepository.findByCustomerId(account.getCustomer().getCustomerId());
        if (customer.isEmpty()){
            return null;
        }
        else{
            Account account1 = new Account();
            account1.setCustomer(account.getCustomer());
            account1.setAccName(account.getAccName());
            account1.setAccBranch(account.getAccBranch());
            account1.setDeposit(account.getDeposit());
            account1.setActualBalance(account1.getDeposit());
            return accountRepository.save(account1);
        }
    }

    /*
    Get Account via account Id
     */
    @Override
    public List<AccountDTO> getAccount(Long id) {
        List<AccountDTO> accountDTOS =accountRepository.findByAccId(id).
                stream().
                map(this::convertToAccountDTO).
                collect(Collectors.toList());

        if (accountDTOS.isEmpty()){
            return null;
        }
        else {
            return accountDTOS;
        }
    }

    /*
    Entity to DTO cnversion
     */
    private AccountDTO convertToAccountDTO(Account account) {
        Customer customer = account.getCustomer();
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setAccId(account.getAccId());
        accountDTO.setAccName(account.getAccName());
        accountDTO.setAccBranch(account.getAccBranch());
        accountDTO.setActualBalance(account.getActualBalance());
        accountDTO.setName(customer.getCustomerName());
        accountDTO.setEmail(customer.getCustomerEmail());
        accountDTO.setPhone(customer.getCustomerPhone());
        return accountDTO;
    }
}
