package com.fincore.wallet.transaction.service;

import com.fincore.wallet.auth.entity.User;
import com.fincore.wallet.auth.repository.UserRepository;
import com.fincore.wallet.transaction.dto.DepositRequest;
import com.fincore.wallet.transaction.dto.TransactionHistoryResponse;
import com.fincore.wallet.transaction.dto.TransferRequest;
import com.fincore.wallet.transaction.dto.WithdrawRequest;
import com.fincore.wallet.transaction.entity.LedgerEntry;
import com.fincore.wallet.transaction.entity.Transaction;
import com.fincore.wallet.transaction.enums.EntryType;
import com.fincore.wallet.transaction.enums.TransactionStatus;
import com.fincore.wallet.transaction.enums.TransactionType;
import com.fincore.wallet.transaction.repository.LedgerEntryRepository;
import com.fincore.wallet.transaction.repository.TransactionRepository;
import com.fincore.wallet.wallet.WalletRepository;
import com.fincore.wallet.wallet.entity.Wallet;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.security.PublicKey;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final WalletRepository walletRepository;
    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;
    private final LedgerEntryRepository ledgerEntryRepository;

    @Transactional
    public String deposit(String email, DepositRequest request){

        Wallet wallet = getWalletByEmail(email);
        BigDecimal balanceBefore = wallet.getBalance();

        wallet.setBalance(wallet.getBalance().add(request.getAmount()));
        BigDecimal balanceAfter = wallet.getBalance();

        walletRepository.save(wallet);

        saveTransaction(wallet, request.getAmount(),
                TransactionType.DEPOSIT,"Wallet deposit",
                balanceBefore, balanceAfter, EntryType.CREDIT);

        return "Deposit successful";

    }

    @Transactional
    public String withdraw(String email, WithdrawRequest request){
        Wallet wallet= getWalletByEmail(email);

        if (wallet.getBalance().compareTo(request.getAmount()) < 0){
            throw new RuntimeException("Insufficient balance");
        }

        BigDecimal balanceBefore = wallet.getBalance();

        wallet.setBalance(wallet.getBalance().subtract(request.getAmount()));

        BigDecimal balanceAfter = wallet.getBalance();

        walletRepository.save(wallet);
        saveTransaction(wallet, request.getAmount(),
                TransactionType.WITHDRAWAL, "Wallet withdrawal",
                balanceBefore, balanceAfter, EntryType.DEBIT);

        return "Withdrawal successful";

    }

    @Transactional
    public String transfer(String email, TransferRequest request){

        Wallet sourceWallet = getWalletByEmail(email);

        Wallet destinationWallet =walletRepository.findByWalletNumber(request.getDestinationWalletNumber()
        ).orElseThrow(() ->
                new RuntimeException("Destination wallet not found"));

        //Prevent self transfer
        if (sourceWallet.getWalletNumber().equals(destinationWallet.getWalletNumber())){
            throw new RuntimeException("You cannot transfer to your own wallet");
        }

        // validate amount
        if (request.getAmount().compareTo(BigDecimal.ZERO) <= 0){
            throw new RuntimeException("Transfer amount must be greater than zero");
        }

        // validate sufficient balance
        if (sourceWallet.getBalance().compareTo(request.getAmount()) <0){
            throw new RuntimeException("Insufficient balance");
        }

        //Source Wallet Processing
        BigDecimal sourceBalanceBefore = sourceWallet.getBalance();
        sourceWallet.setBalance(sourceWallet.getBalance().subtract(request.getAmount()));
        BigDecimal sourceBalanceAfter = sourceWallet.getBalance();

        //Destination Wallet Processing
        BigDecimal destinationBalanceBefore = destinationWallet.getBalance();
        destinationWallet.setBalance(destinationWallet.getBalance().add(request.getAmount()));
        BigDecimal destinationBalanceAfter = destinationWallet.getBalance();

        //SAVE BOTH WALLETS
        walletRepository.save(sourceWallet);
        walletRepository.save(destinationWallet);

        //DEBIT ENTRY FOR SENDER
        saveTransaction(sourceWallet, request.getAmount(),
                TransactionType.TRANSFER,
                "Transfer to " + destinationWallet.getWalletNumber(),
                sourceBalanceBefore, sourceBalanceAfter, EntryType.DEBIT);

        //CREDIT ENTRY FOR RECEIVER
        saveTransaction(destinationWallet, request.getAmount(), TransactionType.TRANSFER,
                "Transfer from " + sourceWallet.getWalletNumber(),
                destinationBalanceBefore, destinationBalanceAfter, EntryType.CREDIT);

        return "Transfer successful";

    }

    public List<TransactionHistoryResponse> getTransactionHistory(String email){
        Wallet wallet = getWalletByEmail(email);

        return ledgerEntryRepository.findByWalletOrderByCreatedAtDesc(wallet)
                .stream()
                .map(entry ->
                        TransactionHistoryResponse.builder()
                                .transactionReference(
                                        entry.getTransaction().getTransactionReference()
                                )
                                .transactionType(
                                        entry.getTransaction().getTransactionType().name()
                                )
                                .amount(entry.getAmount())
                                .entryType(
                                        entry.getEntryType().name()
                                )
                                .balanceBefore(entry.getBalanceBefore()
                                )
                                .balanceAfter(entry.getBalanceAfter()
                                )
                                .narration(entry.getNarration())
                                .transactionDate(
                                        entry.getCreatedAt()
                                )
                                .build()
                )
                .toList();
    }

    private void saveTransaction(
            Wallet wallet, BigDecimal amount,
            TransactionType transactionType,
            String narration, BigDecimal balanceBefore,
            BigDecimal balanceAfter, EntryType entryType) {

        Transaction transaction = new Transaction();

        transaction.setTransactionReference(generateTransactionReference());

        transaction.setWallet(wallet);
        transaction.setAmount(amount);
        transaction.setTransactionType(transactionType);
        transaction.setStatus(TransactionStatus.SUCCESS);
        transaction.setNarration(narration);

        Transaction savedTransaction = transactionRepository.save(transaction);

        LedgerEntry ledgerEntry = new LedgerEntry();
        ledgerEntry.setWallet(wallet);
        ledgerEntry.setTransaction(savedTransaction);
        ledgerEntry.setAmount(amount);
        ledgerEntry.setEntryType(entryType);
        ledgerEntry.setBalanceBefore(balanceBefore);
        ledgerEntry.setBalanceAfter(balanceAfter);
        ledgerEntry.setNarration(narration);

        ledgerEntryRepository.save(ledgerEntry);

       // transactionRepository.save(transaction);

    }

    private String generateTransactionReference() {
        return "TXN-" + UUID.randomUUID()
                .toString()
                .replace("-", "")
                .substring(0, 12)
                .toUpperCase();
    }

    private Wallet getWalletByEmail(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() ->
                new RuntimeException("User not found"));
        return walletRepository.findAll().stream().filter(
                wallet -> wallet.getUser()
                        .getId()
                        .equals(user.getId())
        )
                .findFirst()
                .orElseThrow(() ->
                        new RuntimeException("Wallet not found"));
    }
}
