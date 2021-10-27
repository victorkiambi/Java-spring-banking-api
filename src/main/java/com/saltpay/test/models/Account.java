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
    private  Long acc_id;

    private String acc_name;
    private String acc_branch;
    private Date created_at;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @OneToMany(mappedBy = "account")
    private List<Transaction> transaction;
}
