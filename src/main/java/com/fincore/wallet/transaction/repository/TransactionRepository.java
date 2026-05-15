package com.fincore.wallet.transaction.repository;

import com.fincore.wallet.transaction.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
