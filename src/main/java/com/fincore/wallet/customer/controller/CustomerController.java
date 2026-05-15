package com.fincore.wallet.customer.controller;

import com.fincore.wallet.common.response.ApiResponse;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {

    @GetMapping("/profile")
    public ApiResponse<String> getProfile(Authentication authentication){
        String email = authentication.getName();

        return ApiResponse.<String>builder()
                .success(true)
                .message("Profile fetched successfully")
                .data(email)
                .build();
    }
}
