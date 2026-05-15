package com.fincore.wallet.transaction.repository;

import com.fincore.wallet.transaction.entity.LedgerEntry;
import com.fincore.wallet.wallet.entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LedgerEntryRepository extends JpaRepository<LedgerEntry, Long> {

    List<LedgerEntry> findByWalletOrderByCreatedAtDesc(Wallet wallet);
}
