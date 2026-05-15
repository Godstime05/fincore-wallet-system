package com.fincore.wallet.transaction.controller;

import com.fincore.wallet.common.response.ApiResponse;
import com.fincore.wallet.transaction.dto.DepositRequest;
import com.fincore.wallet.transaction.dto.TransactionHistoryResponse;
import com.fincore.wallet.transaction.dto.TransferRequest;
import com.fincore.wallet.transaction.dto.WithdrawRequest;
import com.fincore.wallet.transaction.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping("/deposit")
    public ApiResponse<String>deposit(Authentication authentication, @RequestBody DepositRequest request){
        return ApiResponse.<String>builder()
                .success(true)
                .message(transactionService.deposit(authentication.getName(), request))
                .build();
    }

    @PostMapping("/withdraw")
    public ApiResponse<String>withdraw(Authentication authentication, @RequestBody WithdrawRequest request){
        return ApiResponse.<String>builder()
                .success(true)
                .message(transactionService.withdraw(authentication.getName(), request))
                .build();
    }

    @PostMapping("/transfer")
    public ApiResponse<String>transfer(Authentication authentication, @RequestBody TransferRequest request){
        return ApiResponse.<String>builder()
                .success(true)
                .message(transactionService.transfer(authentication.getName(), request))
                .build();
    }

    @GetMapping("/history")
    public ApiResponse<List<TransactionHistoryResponse>> getHistory(Authentication auth){
        return ApiResponse.<List<TransactionHistoryResponse>>builder()
                .success(true)
                .message("Transaction history fetched successfully")
                .data(transactionService.getTransactionHistory(auth.getName()
                )).build();
    }
}
