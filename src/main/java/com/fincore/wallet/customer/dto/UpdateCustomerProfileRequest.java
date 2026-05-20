package com.fincore.wallet.customer.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class UpdateCustomerProfileRequest {

    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String address;
    private LocalDate dateOfBirth;
}
