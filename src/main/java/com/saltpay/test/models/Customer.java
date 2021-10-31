package com.saltpay.test.models;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Customer {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long customerId;

    private String customerName;

    private String customerEmail;

    private Integer customerPhone;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.MERGE)
    private List<Account> accounts = new ArrayList<>();

    public Customer(Long customerId, String customerName, String customerEmail, Integer customerPhone) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.customerPhone = customerPhone;
    }

    public void addAccount(Account account) {
        accounts.add(account);
        account.setCustomer(this);
    }

    public void removeAccount(Account account){
        accounts.remove(account);
        account.setCustomer(null);
    }
}
