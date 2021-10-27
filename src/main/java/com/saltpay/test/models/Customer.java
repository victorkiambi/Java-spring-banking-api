package com.saltpay.test.models;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NotFound;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Customer {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long customer_id;

    @NotNull()
    private String name;

    @NotNull()
    private String email;

    @NotNull
    private Integer phone;

    @OneToMany(mappedBy = "customer")
    private List<Account> accounts;


}
