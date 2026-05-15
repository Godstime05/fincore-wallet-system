package com.fincore.wallet.transaction.entity;

import com.fincore.wallet.common.entity.BaseEntity;
import com.fincore.wallet.transaction.enums.EntryType;
import com.fincore.wallet.wallet.entity.Wallet;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "ledger_entries")
public class LedgerEntry extends BaseEntity {

    @Enumerated(EnumType.STRING)
    private EntryType entryType;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = false)
    private BigDecimal balanceBefore;

    @Column(nullable = false)
    private BigDecimal balanceAfter;

    private String narration;

    @ManyToOne
    @JoinColumn(name = "wallet_id")
    private Wallet wallet;

    @ManyToOne
    @JoinColumn(name = "transaction_id")
    private Transaction transaction;
}
