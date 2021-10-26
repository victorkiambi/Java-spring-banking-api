package com.saltpay.test.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.repository.cdi.Eager;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long transaction_id;


    private double amount;
    private Date created_at;

    private TransactionType transactionType;

    @ManyToOne
    @JoinColumn(name = "acc_id")
    private Account account;
}
