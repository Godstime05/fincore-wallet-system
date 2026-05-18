package com.fincore.wallet.customer.controller;

import com.fincore.wallet.common.response.ApiResponse;
import com.fincore.wallet.customer.dto.CreateCustomerProfileRequest;
import com.fincore.wallet.customer.dto.CustomerProfileResponse;
import com.fincore.wallet.customer.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;

    @PostMapping("/profile")
    public ApiResponse<String> createProfile(Authentication authentication,
            @RequestBody CreateCustomerProfileRequest request) {

        return ApiResponse.<String>builder()
                .success(true)
                .message(customerService.createProfile(authentication.getName(), request))
                .build();
    }

    @GetMapping("/profile")
    public ApiResponse<CustomerProfileResponse> getProfile(Authentication authentication){
//        String email = authentication.getName();

        return ApiResponse.<CustomerProfileResponse>builder()
                .success(true)
                .message("Profile fetched successfully")
                .data(customerService.getProfile(authentication.getName()))
                .build();
    }
}
