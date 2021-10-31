package com.saltpay.test.models;

import com.fasterxml.jackson.annotation.JsonProperty;
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
    private double transactionAmount;
    private TransactionType transactionType;

//    @Transient
    private transient Long senderAccNo;

//    @Transient
    private transient Long receiverAccNo;

    private transient String transactionDetails;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "acc_No")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Account account;


}
