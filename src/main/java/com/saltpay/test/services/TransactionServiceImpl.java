package com.saltpay.test.services;

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
    private TransactionRepository transactionRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public List<TransactionDTO> findTransactionsByAccount(Long accNo) {

        List<Transaction> transactionDTOS = transactionRepository.findTransactionByAccount_AccNo(accNo);
        return transactionDTOS.
                stream().
                map(this::convertToTransactionDTO).collect(Collectors.toList());
    }

    @Override
    public Account createTransaction(Transaction transaction) {
        List<Account> accounts = getActualBalance(transaction.getSenderAccNo());

        if (accounts.isEmpty()){
            return null;
        }
        else{
            double minBalance = accounts.get(0).getMinBalance();
            double depositedAmount = transaction.getTransactionAmount();
            double newBalance = minBalance + depositedAmount;

            Account account = getAccount(transaction.getSenderAccNo());

            return setBalance(account, newBalance, transaction);
//            account.setMinBalance(newBalance);
//
//            Transaction transaction1 = new Transaction();
//            transaction1.setTransactionType(TransactionType.DEPOSIT);
//            transaction1.setTransactionAmount(transaction.getTransactionAmount());
//            account.addTransaction(transaction);
//            return accountRepository.save(account);

        }
    }


    @Override
    public Account bankTransfer(Transaction newTransaction) {
        List<Account> accounts = getActualBalance(newTransaction.getSenderAccNo());

        if (accounts.isEmpty())
            return null;

        if ((accounts.get(0).getMinBalance() == 0) || (accounts.get(0).getMinBalance() < newTransaction.getTransactionAmount())){
            return null;
        }
        else{


            double senderMinBalance = accounts.get(0).getMinBalance();
            double depositedAmount = newTransaction.getTransactionAmount();
            double newSenderBalance = senderMinBalance - depositedAmount;
            Account account = accountRepository.getAccountByAccNo(newTransaction.getSenderAccNo());
            Account accounts1 = setBalance(account, newSenderBalance,newTransaction);

            List<Account> receiverAccount = getActualBalance(newTransaction.getReceiverAccNo());
            double receiverMinBalance = receiverAccount.get(0).getMinBalance();
            double newReceiverBalance = receiverMinBalance + depositedAmount;

            Account account1 = getAccount(newTransaction.getReceiverAccNo());
            return setBalance(account1,newReceiverBalance, newTransaction);
        }
    }

    private Account setBalance(Account account, double newBalance, Transaction newTransaction) {
        account.setMinBalance(newBalance);

        Transaction transaction = new Transaction();
        newTransaction.setTransactionType(TransactionType.DEPOSIT);
        newTransaction.setTransactionAmount(newTransaction.getTransactionAmount());
        account.addTransaction(newTransaction);
        return accountRepository.save(account);
    }


    private List<Account> getActualBalance(Long senderAccNo) {
        return accountRepository.findByAccNo(senderAccNo);
    }

    private Account getAccount(Long senderAccNo) {
        return accountRepository.getAccountByAccNo(senderAccNo);
    }

//    public List<Account> getActualBalance(accNo){
//    }

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
