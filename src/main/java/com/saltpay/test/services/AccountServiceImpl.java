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
    private final AccountRepository accountRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private TransactionRepository transactionRepository;

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
    public List<AccountDTO> getAccount(Long id) {
        List<AccountDTO> accountDTOS =accountRepository.findByAccNo(id).
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
        accountDTO.setAccNo(account.getAccNo());
        accountDTO.setAccName(account.getAccName());
        accountDTO.setAccBranch(account.getAccBranch());
        accountDTO.setMinBalance(account.getMinBalance());
        accountDTO.setName(customer.getCustomerName());
        accountDTO.setEmail(customer.getCustomerEmail());
        accountDTO.setPhone(customer.getCustomerPhone());
        accountDTO.setTransaction(account.getTransactions());
        return accountDTO;
    }
}
