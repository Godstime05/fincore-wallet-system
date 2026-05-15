package com.fincore.wallet.wallet.service;

import com.fincore.wallet.auth.entity.User;
import com.fincore.wallet.auth.repository.UserRepository;
import com.fincore.wallet.wallet.WalletRepository;
import com.fincore.wallet.wallet.dto.WalletResponse;
import com.fincore.wallet.wallet.entity.Wallet;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class WalletService {
    private final WalletRepository walletRepository;
    private final UserRepository userRepository;

    public Wallet createWallet(User user){
        Wallet wallet = new Wallet();

        wallet.setWalletNumber(generateWalletNumber());
        wallet.setBalance(BigDecimal.ZERO);
        wallet.setUser(user);

        return walletRepository.save(wallet);
    }

    private String generateWalletNumber() {
        Random random = new Random();
        return "90" + (10000000 + random.nextInt(90000000));

    }
    public WalletResponse getMyWallet(String email){
        User user = userRepository.findByEmail(email).orElseThrow(() ->
                new RuntimeException("User not found"));
        Wallet wallet = walletRepository.findAll()
                .stream()
                .filter(w -> w.getUser().getId().equals(user.getId()))
                .findFirst()
                .orElseThrow(() ->
                        new RuntimeException("Wallet not found"));
        return WalletResponse.builder()
                .walletNumber(wallet.getWalletNumber())
                .balance(wallet.getBalance())
                .status(wallet.getStatus().name())
                .build();
    }
}
