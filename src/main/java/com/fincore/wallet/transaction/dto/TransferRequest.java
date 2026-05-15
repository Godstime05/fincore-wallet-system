package com.fincore.wallet.transaction.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransferRequest {

    private String destinationWalletNumber;
    private BigDecimal amount;
}
