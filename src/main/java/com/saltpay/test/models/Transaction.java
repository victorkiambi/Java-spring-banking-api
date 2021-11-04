package com.saltpay.test.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Transaction {

    @Id
    @GeneratedValue
    private Long transactionId;
    private UUID transactionReferenceNo = UUID.randomUUID();

    @NotNull
    private double transactionAmount;
    @NotNull
    private TransactionType transactionType;

    @NotNull
    private transient Long senderAccNo;

    @NotNull
    private transient Long receiverAccNo;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "acc_No")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Account account;

    public Transaction(Long senderAccNo, double transactionAmount ){
        this.senderAccNo = senderAccNo;
        this.transactionAmount = transactionAmount;
    }

}
