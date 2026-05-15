package com.fincore.wallet.transaction.entity;

import com.fincore.wallet.common.entity.BaseEntity;
import com.fincore.wallet.transaction.enums.TransactionStatus;
import com.fincore.wallet.transaction.enums.TransactionType;
import com.fincore.wallet.wallet.entity.Wallet;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "transactions")
public class Transaction extends BaseEntity {
    @Column(nullable = false, unique = true)
    private String transactionReference;

    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;

    @Enumerated(EnumType.STRING)
    private TransactionStatus status;

    @Column(nullable = false)
    private BigDecimal amount;

    private String narration;

    @ManyToOne
    @JoinColumn(name = "wallet_id")
    private Wallet wallet;

}
