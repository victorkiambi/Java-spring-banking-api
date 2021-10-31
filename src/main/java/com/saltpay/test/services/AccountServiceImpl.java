package com.saltpay.test.services;

import com.saltpay.test.DTO.AccountDTO;
import com.saltpay.test.models.Account;
import com.saltpay.test.models.Customer;
import com.saltpay.test.models.Transaction;
import com.saltpay.test.models.TransactionType;
import com.saltpay.test.repositories.AccountRepository;
import com.saltpay.test.repositories.CustomerRepository;
import com.saltpay.test.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    public final AccountRepository accountRepository;

    @Autowired
    public CustomerRepository customerRepository;

    @Autowired
    public TransactionRepository transactionRepository;

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
        Customer customer = customerRepository.findByCustomerId(account.getCustomer().getCustomerId());
        if (customer == null){
            return null;
        }
        else{

            //Set account details
            Account account1 = new Account();
            account1.setCustomer(account.getCustomer());
            account1.setAccName(account.getAccName());
            account1.setAccBranch(account.getAccBranch());
            account1.setMinBalance(account.getMinBalance());

            //Update minBalance as transaction
            Transaction transaction = new Transaction();
            transaction.setTransactionAmount(account1.getMinBalance());
            transaction.setTransactionType(TransactionType.DEPOSIT);

            account1.addTransaction(transaction);
            return accountRepository.save(account1);

        }
    }

    /*
    Get Account via account Id
     */
    @Override
    public AccountDTO getAccount(Long id) {
        Account account = accountRepository.findByAccNo(id);

        if (account == null){
            return null;
        }
        else {

            AccountDTO accountDTO = new AccountDTO();
            Customer customer = account.getCustomer();

            return getAccountDTO(account, accountDTO, customer);
        }
    }

    private AccountDTO getAccountDTO(Account account, AccountDTO accountDTO, Customer customer) {
        accountDTO.setAccNo(account.getAccNo());
        accountDTO.setAccName(account.getAccName());
        accountDTO.setAccBranch(account.getAccBranch());
        accountDTO.setMinBalance(account.getMinBalance());
        accountDTO.setName(customer.getCustomerName());
        accountDTO.setEmail(customer.getCustomerEmail());
        accountDTO.setPhone(customer.getCustomerPhone());

        return accountDTO;
    }

    /*
    Entity to DTO cnversion
     */
    private AccountDTO convertToAccountDTO(Account account) {
        Customer customer = account.getCustomer();
        AccountDTO accountDTO = new AccountDTO();
        return getAccountDTO(account, accountDTO, customer);
    }
}
