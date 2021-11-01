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

    /**
     * Creates new account
     * @param account
     * @return AccountDTO
     */
    @Override
    public AccountDTO saveAccount(Account account) {

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

            //Update minBalance as a transaction
            Transaction transaction = new Transaction();
            transaction.setTransactionAmount(account1.getMinBalance());
            transaction.setTransactionType(TransactionType.DEPOSIT);

            account1.addTransaction(transaction);
            Account response = accountRepository.save(account1);

            //Return DTO
            AccountDTO accountDTO = new AccountDTO();
            accountDTO.setAccNo(response.getAccNo());
            accountDTO.setAccName(response.getAccName());
            accountDTO.setMinBalance(response.getMinBalance());
            accountDTO.setAccBranch(response.getAccBranch());

            accountDTO.setName(customer.getCustomerName());
            accountDTO.setEmail(customer.getCustomerEmail());
            accountDTO.setPhone(customer.getCustomerPhone());

            return accountDTO;

        }
    }

    /**
     * Get single account details
     * @param id
     * @return AccountDTO
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

    /**
     * Converts Account entity to AccountDTO
     * @param account
     * @param accountDTO
     * @param customer
     * @return AccountDTO
     */
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

}
