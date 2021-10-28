package com.saltpay.test.models;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Customer {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long customerId;

    private String name;

    private String email;

    private Integer phone;

    @OneToMany(mappedBy = "customer")
    private List<Account> accounts;


}
