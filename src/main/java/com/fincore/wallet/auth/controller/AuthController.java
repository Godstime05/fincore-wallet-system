package com.fincore.wallet.auth.controller;

import com.fincore.wallet.auth.dto.AuthResponse;
import com.fincore.wallet.auth.dto.LoginRequest;
import com.fincore.wallet.auth.dto.RegisterRequest;
import com.fincore.wallet.auth.service.AuthService;
import com.fincore.wallet.common.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ApiResponse<AuthResponse> register(@RequestBody RegisterRequest request){
        return ApiResponse.<AuthResponse>builder()
                .success(true)
                .message("User registered successfully")
                .data(authService.register(request))
                .build();
    }

    @PostMapping("/login")
    public ApiResponse<AuthResponse>login(@RequestBody LoginRequest request){
        return ApiResponse.<AuthResponse>builder()
                .success(true)
                .message("Login Successful")
                .data(authService.login(request))
                .build();
    }

    @PostMapping("/register-admin")
    public ApiResponse<AuthResponse> registerAdmin(@RequestBody RegisterRequest request){
        return ApiResponse.<AuthResponse>builder()
                .success(true)
                .message("Admin registered successfully")
                .data(authService.registerAdmin(request))
                .build();
    }

}
