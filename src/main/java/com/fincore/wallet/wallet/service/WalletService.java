package com.fincore.wallet.wallet.service;

import com.fincore.wallet.audit.service.AuditService;
import com.fincore.wallet.auth.entity.User;
import com.fincore.wallet.auth.repository.UserRepository;
import com.fincore.wallet.common.utils.NumberGeneratorUtil;
import com.fincore.wallet.exception.BusinessException;
import com.fincore.wallet.wallet.dto.CreateWalletRequest;
import com.fincore.wallet.wallet.enums.WalletStatus;
import com.fincore.wallet.wallet.repository.WalletRepository;
import com.fincore.wallet.wallet.dto.WalletResponse;
import com.fincore.wallet.wallet.entity.Wallet;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class WalletService {
    private final WalletRepository walletRepository;
    private final UserRepository userRepository;
    private final AuditService auditService;

    public Wallet createWallet(User user){

        Optional<Wallet> existingWallet = walletRepository.findByUser(user);

        if (existingWallet.isPresent()){
            throw new BusinessException("User already has a wallet");
        }

        Wallet wallet = new Wallet();

//        wallet.setWalletNumber(generateWalletNumber());
        long walletCount = walletRepository.count() + 1;
        wallet.setWalletId(NumberGeneratorUtil.generateWalletId(walletCount));
        wallet.setWalletNumber(NumberGeneratorUtil.generateWalletNumber(walletCount));

        wallet.setBalance(BigDecimal.ZERO);
        wallet.setStatus(WalletStatus.ACTIVE);
        wallet.setUser(user);

        return walletRepository.save(wallet);
    }

//    private String generateWalletNumber() {
//        Random random = new Random();
//        return "90" + (10000000 + random.nextInt(90000000));
//
//    }
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

    public String freezeWallet(String walletNumber){
        Wallet wallet = walletRepository.findByWalletNumber(walletNumber)
                .orElseThrow(() -> new RuntimeException("Wallet not found"));

        wallet.setStatus(WalletStatus.FROZEN);
        walletRepository.save(wallet);

        auditService.logAction(
                "FREEZE_WALLET",
                "ADMIN",
                "WALLET",
                "Wallet " + walletNumber + " frozen",
                "SUCCESS"
        );

        return "Wallet frozen successfully";
    }

    public String unfreezeWallet(String walletNumber){
        Wallet wallet = walletRepository.findByWalletNumber(walletNumber)
                .orElseThrow(() -> new RuntimeException("Wallet not found"));

        wallet.setStatus(WalletStatus.ACTIVE);
        walletRepository.save(wallet);

        auditService.logAction(
                "UNFREEZE_WALLET",
                "ADMIN",
                "WALLET",
                "Wallet " + walletNumber + " unfrozen",
                "SUCCESS"
        );
        return "Wallet unfrozen successfully";
    }

    public String createWalletForUser(CreateWalletRequest request){

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new BusinessException("User not found"));

        createWallet(user);

        auditService.logAction(
                "CREATE_WALLET",
                "ADMIN",
                "WALLET",
                "Wallet manually created for " + user.getEmail(),
                "SUCCESS"
        );

        return "Wallet created successfully";

    }

}
