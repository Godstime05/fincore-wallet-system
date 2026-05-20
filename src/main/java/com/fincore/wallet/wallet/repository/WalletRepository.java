package com.fincore.wallet.wallet.repository;

import com.fincore.wallet.auth.entity.User;
import com.fincore.wallet.wallet.entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WalletRepository extends JpaRepository<Wallet, Long> {

    Optional<Wallet> findByWalletNumber(String walletNumber);
    Optional<Wallet> findByUser (User user);

    //@Override
    long count();
}
