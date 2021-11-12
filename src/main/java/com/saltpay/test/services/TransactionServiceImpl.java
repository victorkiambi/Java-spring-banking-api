package com.saltpay.test.services;

import com.saltpay.test.DTO.AccountTransactionDTO;
import com.saltpay.test.DTO.TransactionDTO;
import com.saltpay.test.models.Account;
import com.saltpay.test.models.Transaction;
import com.saltpay.test.models.TransactionType;
import com.saltpay.test.repositories.AccountRepository;
import com.saltpay.test.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionServiceImpl implements TransactionService{

    @Autowired
    public TransactionRepository transactionRepository;

    @Autowired
    public AccountRepository accountRepository;

    /**
     * Retrieves all transactions based on account no.
     * @param accNo
     * @return all transactions history
     */
    @Override
    public List<TransactionDTO> findTransactionsByAccount(Long accNo) {

        List<Transaction> transactionDTOS = transactionRepository.findTransactionByAccount_AccNo(accNo);

        //check if account exists
        if (transactionDTOS == null) {
            return null;
        }

        else{
            return transactionDTOS.
                    stream().
                    map(this::convertToTransactionDTO).collect(Collectors.toList());
        }
    }

    /**
     * Handles deposits to own personal account
     * @param transaction
     * @return new account balance details
     */
    @Override
    public AccountTransactionDTO depositToOwnAccount(Transaction transaction) {

        Account accounts = getActualBalance(transaction.getSenderAccNo());

        //check if account exists
        if (accounts == null){
            return null;
        }
        else{
            //add new amount to minimum balance
            double minBalance = accounts.getMinBalance();
            double depositedAmount = transaction.getTransactionAmount();
            double newBalance = minBalance + depositedAmount;

            Account account = getAccount(transaction.getSenderAccNo());
            Transaction transaction1 = new Transaction();
            transaction1.setTransactionType(TransactionType.DEPOSIT);
            transaction1.setTransactionAmount(depositedAmount);
            transaction1.setReceiverAccNo(transaction.getSenderAccNo());

            return setBalance(account, newBalance, transaction1);
        }
    }

    /**
     * Handles account to account transfer i.e customer to customer
     * @param newTransaction
     * @return new account balance details
     */
    @Override
    public AccountTransactionDTO accountToAccountTransfer(Transaction newTransaction) {
        Account senderAccount = getActualBalance(newTransaction.getSenderAccNo());
        Account receiverAccount = getActualBalance(newTransaction.getReceiverAccNo());

        //check if both sender and receiver accounts exist
        if ((senderAccount == null) || (receiverAccount == null)  ) {
          return null;
        }
        //check if sender account is 0 or less than amount to be withdrawn
        if ((senderAccount.getMinBalance() == 0) || (senderAccount.getMinBalance() < newTransaction.getTransactionAmount())){
           return null;
        }
        else{
            //subtract amount from sender
            double senderMinBalance = senderAccount.getMinBalance();
            double depositedAmount = newTransaction.getTransactionAmount();
            double newSenderBalance = senderMinBalance - depositedAmount;

            Account account = accountRepository.getAccountByAccNo(newTransaction.getSenderAccNo());
            Transaction senderTransaction = new Transaction();
            senderTransaction.setTransactionType(TransactionType.TRANSFER_OUT);
            senderTransaction.setTransactionAmount(newTransaction.getTransactionAmount());
            senderTransaction.setReceiverAccNo(newTransaction.getReceiverAccNo());


            //update new balance
            AccountTransactionDTO senAccount = setBalance(account, newSenderBalance, senderTransaction);

            //add subtracted amount to receiver
            double receiverMinBalance = receiverAccount.getMinBalance();
            double newReceiverBalance = receiverMinBalance + depositedAmount;

            Account account1 = getAccount(newTransaction.getReceiverAccNo());
            Transaction receiverTransaction = new Transaction();
            receiverTransaction.setTransactionType(TransactionType.TRANSFER_IN);
            receiverTransaction.setTransactionAmount(depositedAmount);

            //update receiver balance
            setBalance(account1,newReceiverBalance, receiverTransaction);

            return senAccount;

        }
    }

    /**
     * Converts Account and Transaction entities to AccountTransactionDTO
     * @param account
     * @param newBalance
     * @param newTransaction
     * @return
     */
    private AccountTransactionDTO setBalance(Account account, double newBalance, Transaction newTransaction) {

        account.setMinBalance(newBalance);
        Transaction transaction = new Transaction();
        transaction.setTransactionType(newTransaction.getTransactionType());
        transaction.setTransactionAmount(newTransaction.getTransactionAmount());
        account.addTransaction(transaction);
        Account response = accountRepository.save(account);

        AccountTransactionDTO accountTransactionDTO = new AccountTransactionDTO();
        accountTransactionDTO.setAccNo(response.getAccNo());
        accountTransactionDTO.setAccName(response.getAccName());
        accountTransactionDTO.setAccBranch(response.getAccBranch());
        accountTransactionDTO.setMinBalance(response.getMinBalance());
        accountTransactionDTO.setTransactionAmount(newTransaction.getTransactionAmount());
        accountTransactionDTO.setReceiverAccNo(newTransaction.getReceiverAccNo());

        return accountTransactionDTO;
    }


    /**
     * Retrieves sender/receiver account balance
     * @param accNo
     * @return
     */
    private Account getActualBalance(Long accNo) {
        return accountRepository.findByAccNo(accNo);
    }

    /**
     * Retrieves sender/receiver account details
     * @param accNo
     * @return
     */
    private Account getAccount(Long accNo) {
        return accountRepository.getAccountByAccNo(accNo);
    }


    /**
    * Transaction Entity to DTO cnversion
    */
    private TransactionDTO convertToTransactionDTO(Transaction transaction) {
        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setTransactionReferenceNo(transaction.getTransactionReferenceNo());
        transactionDTO.setTransactionType(transaction.getTransactionType());
        transactionDTO.setTransactionAmount(transaction.getTransactionAmount());
        return transactionDTO;
    }

}
