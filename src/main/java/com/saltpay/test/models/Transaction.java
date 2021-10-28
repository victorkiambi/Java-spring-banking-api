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
    private Long transactionId;
    private double amount;

    private TransactionType transactionType;

    @ManyToOne
    @JoinColumn(name = "accId", insertable = false, updatable = false)
    private Account account;
}
