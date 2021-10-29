package com.saltpay.test.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.stream.DoubleStream;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Account{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "account_generator")
    @SequenceGenerator(name="account_generator", sequenceName = "account_seq", initialValue = 1000, allocationSize=50)
    private Long accId;
    private String accName;
    private String accBranch;
    private double deposit;
    private double actualBalance;

    @ManyToOne( fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)

    private Customer customer;


}
