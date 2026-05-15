package com.fincore.wallet.transaction.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class TransactionHistoryResponse {
    private String transactionReference;
    private String transactionType;
    private BigDecimal amount;
    private String entryType;
    private BigDecimal balanceBefore;
    private BigDecimal balanceAfter;
    private String narration;
    private LocalDateTime transactionDate;

}
