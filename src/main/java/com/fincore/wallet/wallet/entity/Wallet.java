package com.fincore.wallet.wallet.entity;

import com.fincore.wallet.auth.entity.User;
import com.fincore.wallet.common.entity.BaseEntity;
import com.fincore.wallet.wallet.enums.WalletStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "wallets")
public class Wallet extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String walletId;

    @Column(nullable = false, unique = true)
    private String walletNumber;

    @Column(nullable = false)
    private BigDecimal balance = BigDecimal.ZERO;

    @Enumerated(EnumType.STRING)
    private WalletStatus status = WalletStatus.ACTIVE;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
}
