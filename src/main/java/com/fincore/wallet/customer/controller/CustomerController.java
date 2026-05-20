package com.fincore.wallet.customer.controller;

import com.fincore.wallet.common.response.ApiResponse;
import com.fincore.wallet.customer.dto.CreateCustomerProfileRequest;
import com.fincore.wallet.customer.dto.CustomerProfileResponse;
import com.fincore.wallet.customer.dto.UpdateCustomerProfileRequest;
import com.fincore.wallet.customer.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;

    @PostMapping("/profile")
    public ApiResponse<String> createProfile( @Valid @RequestBody CreateCustomerProfileRequest request) {

        return ApiResponse.<String>builder()
                .success(true)
                .message(customerService.createProfile(request))
                .build();
    }

    @GetMapping("/profile")
    public ApiResponse<CustomerProfileResponse> getProfile(Authentication authentication){

        return ApiResponse.<CustomerProfileResponse>builder()
                .success(true)
                .message("Profile fetched successfully")
                .data(customerService.getProfile(authentication.getName()))
                .build();
    }

    @PutMapping("/profile")
    public ApiResponse<String> updateProfile(Authentication authentication, @RequestBody UpdateCustomerProfileRequest request){

        return ApiResponse.<String>builder()
                .success(true)
                .message(customerService.updateProfile(authentication.getName(), request))
                .build();
    }

    @PutMapping("/{customerNumber}")
    public ApiResponse<String> adminUpdateProfile(@PathVariable String customerNumber,
                                                  @RequestBody UpdateCustomerProfileRequest request) {

        return ApiResponse.<String>builder()
                .success(true)
                .message(customerService.adminUpdateProfile(customerNumber, request))
                .build();
    }


}
