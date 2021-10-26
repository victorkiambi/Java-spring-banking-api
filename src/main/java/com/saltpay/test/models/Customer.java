package com.saltpay.test.models;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Customer {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long customer_id;

    private String name;
    private String email;
    private Integer phone;

    @OneToMany(mappedBy = "user_id")
    private List<Account> accounts;


}
