package com.saltpay.test.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.DoubleStream;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Account{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "account_generator")
    @SequenceGenerator(name="account_generator", sequenceName = "account_seq", initialValue = 1000, allocationSize=50)

    private Long accNo;
    private String accName;
    private String accBranch;
    private double minBalance;

    private transient double transactionAmount;
    private transient Long receiverAccNo;

    @ManyToOne( fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Customer customer;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Transaction> transactions = new ArrayList<>();


    public Account(String accName, String accBranch, double minBalance, Customer customer) {
        this.accName = accName;
        this.accBranch = accBranch;
        this.minBalance = minBalance;
        this.customer = customer;
    }

    public Account (Long accNo, double transactionAmount) {
        this.accNo = accNo;
        this.transactionAmount = transactionAmount;
    }




    public void addTransaction(Transaction transaction){
        transactions.add(transaction);
        transaction.setAccount(this);
    }

    public void removeTransaction(Transaction transaction){
        transactions.remove(transaction);
        transaction.setAccount(null);
    }



}
