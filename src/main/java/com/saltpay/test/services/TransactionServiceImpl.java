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

    @Override
    public List<TransactionDTO> findTransactionsByAccount(Long accNo) {

        List<Transaction> transactionDTOS = transactionRepository.findTransactionByAccount_AccNo(accNo);
        if (transactionDTOS == null) {
            return null;
        }
        else{
            return transactionDTOS.
                    stream().
                    map(this::convertToTransactionDTO).collect(Collectors.toList());

    }
    }

    @Override
    public AccountTransactionDTO depositToOwnAccount(Account transaction) {
        Account accounts = getActualBalance(transaction.getAccNo());

        if (accounts == null){
            return null;
        }
        else{
            double minBalance = accounts.getMinBalance();
            double depositedAmount = transaction.getTransactionAmount();
            double newBalance = minBalance + depositedAmount;

            Account account = getAccount(transaction.getAccNo());
            Transaction transaction1 = new Transaction();
            transaction1.setTransactionType(TransactionType.DEPOSIT);
            transaction1.setTransactionAmount(depositedAmount);

            return setBalance(account, newBalance, transaction1);
        }
    }


    @Override
    public AccountTransactionDTO accountToAccountTransfer(Account newTransaction) {
        Account senderAccount = getActualBalance(newTransaction.getAccNo());
        Account receiverAccount = getActualBalance(newTransaction.getReceiverAccNo());

        if ((senderAccount == null) || (receiverAccount == null)  ) {
          return null;
        }
        if ((senderAccount.getMinBalance() == 0) || (senderAccount.getMinBalance() < newTransaction.getTransactionAmount())){
           return null;
        }
        else{

            double senderMinBalance = senderAccount.getMinBalance();
            double depositedAmount = newTransaction.getTransactionAmount();
            double newSenderBalance = senderMinBalance - depositedAmount;

            Account account = accountRepository.getAccountByAccNo(newTransaction.getAccNo());
            Transaction senderTransaction = new Transaction();
            senderTransaction.setTransactionType(TransactionType.TRANSFER_OUT);


            setBalance(account, newSenderBalance, senderTransaction);

            double receiverMinBalance = receiverAccount.getMinBalance();
            double newReceiverBalance = receiverMinBalance + depositedAmount;

            Account account1 = getAccount(newTransaction.getReceiverAccNo());
            Transaction receiverTransaction = new Transaction();
            receiverTransaction.setTransactionType(TransactionType.TRANSFER_IN);

            return setBalance(account1,newReceiverBalance, receiverTransaction);
        }
    }

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
        accountTransactionDTO.setReceiverAccNo(response.getReceiverAccNo());
//        accountTransactionDTO.setTransaction(response.getTransactions()
//                .stream().map(this::convertToTransactionDTO).collect(Collectors.toList()));

        return accountTransactionDTO;
    }


    private Account getActualBalance(Long senderAccNo) {
        return accountRepository.findByAccNo(senderAccNo);
    }

    private Account getAccount(Long senderAccNo) {
        return accountRepository.getAccountByAccNo(senderAccNo);
    }


    /*
   Entity to DTO cnversion
    */
    private TransactionDTO convertToTransactionDTO(Transaction transaction) {
        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setTransactionReferenceNo(transaction.getTransactionReferenceNo());
        transactionDTO.setTransactionType(transaction.getTransactionType());
        transactionDTO.setTransactionAmount(transaction.getTransactionAmount());
        return transactionDTO;
    }

}
