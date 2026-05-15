package com.fincore.wallet.auth.service;

import com.fincore.wallet.auth.dto.AuthResponse;
import com.fincore.wallet.auth.dto.LoginRequest;
import com.fincore.wallet.auth.dto.RegisterRequest;
import com.fincore.wallet.auth.entity.User;
import com.fincore.wallet.auth.repository.UserRepository;
import com.fincore.wallet.common.enums.Role;
import com.fincore.wallet.security.JwtService;
import com.fincore.wallet.wallet.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final WalletService walletService;

    public AuthResponse register(RegisterRequest request){
        User user = new User();
        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.CUSTOMER);

        userRepository.save(user);
        walletService.createWallet(user);
        String token = jwtService.generateToken(user.getEmail());

        return  AuthResponse.builder()
                .token(token)
                .build();
    }

    public AuthResponse login(LoginRequest request){

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())){
            throw new RuntimeException("Invalid Credentials");
        }
        String token = jwtService.generateToken(user.getEmail());
        return AuthResponse.builder()
                .token(token)
                .build();
    }
}
