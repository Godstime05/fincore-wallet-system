package com.fincore.wallet.customer.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CreateCustomerProfileRequest {

    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;

    @Email
    private String email;

    @NotBlank
    private String phoneNumber;

    private String bvn;

    private String nin;

    private LocalDate dateOfBirth;

    private String address;
}
