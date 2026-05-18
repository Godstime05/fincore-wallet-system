package com.fincore.wallet.transaction.repository;

import com.fincore.wallet.transaction.entity.Transaction;
import com.fincore.wallet.transaction.enums.TransactionStatus;
import com.fincore.wallet.transaction.enums.TransactionType;
import com.fincore.wallet.wallet.entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {


    @Query("""
            SELECT COALESCE(SUM(t.amount), 0)
            FROM Transaction t
            WHERE t.wallet = :wallet
            AND t.transactionType = :transactionType 
            AND t.status = :status
            AND t.createdAt BETWEEN :start AND :end
            """)
    BigDecimal getDailyTransactionTotal(
            Wallet wallet, TransactionType transactionType,
                                        TransactionStatus status, LocalDateTime start, LocalDateTime end);

}
