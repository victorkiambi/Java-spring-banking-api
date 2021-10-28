package com.saltpay.test.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Account{
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private  Long accId;

    private String accName;
    private String accBranch;
    private Long customerId;
    @ManyToOne
    @JoinColumn(name = "customerId", insertable = false, updatable = false)
    private Customer customer;

    @OneToMany(mappedBy = "account")
    private List<Transaction> transaction;
}
