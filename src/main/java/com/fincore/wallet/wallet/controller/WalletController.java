package com.fincore.wallet.wallet.controller;

import com.fincore.wallet.common.response.ApiResponse;
import com.fincore.wallet.wallet.dto.WalletResponse;
import com.fincore.wallet.wallet.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/wallets")
@RequiredArgsConstructor
public class WalletController {
    private final WalletService walletService;

    @GetMapping("/my-wallet")
    public ApiResponse<WalletResponse> getMyWallet(Authentication authentication){
        String email = authentication.getName();
        return ApiResponse.<WalletResponse>builder()
                .success(true)
                .message("Wallet fetched Successfully")
                .data(walletService.getMyWallet(email))
                .build();
    }
}
