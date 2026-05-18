package com.fincore.wallet.customer.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class CreateCustomerProfileRequest {

    private String firstName;

    private String lastName;

    private String phoneNumber;

    private String bvn;

    private String nin;

    private LocalDate dateOfBirth;

    private String address;
}
