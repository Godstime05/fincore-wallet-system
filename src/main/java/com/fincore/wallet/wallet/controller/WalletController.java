package com.fincore.wallet.wallet.controller;

import com.fincore.wallet.common.response.ApiResponse;
import com.fincore.wallet.wallet.dto.WalletResponse;
import com.fincore.wallet.wallet.dto.WalletStatusRequest;
import com.fincore.wallet.wallet.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/freeze")
    public ApiResponse<String> freezeWallet(@RequestBody WalletStatusRequest request){
        return ApiResponse.<String>builder()
                .success(true)
                .message(walletService.freezeWallet(request.getWalletNumber()))
                .build();
    }

    @PostMapping("/unfreeze")
    public ApiResponse<String> unfreezeWallet(@RequestBody WalletStatusRequest request){
        return ApiResponse.<String>builder()
                .success(true)
                .message(walletService.unfreezeWallet(request.getWalletNumber()))
                .build();
    }

}
