package com.fincore.wallet.customer.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class CustomerProfileResponse {

    private String firstName;

    private String lastName;

    private String phoneNumber;

    private String bvn;

    private String nin;

    private LocalDate dateOfBirth;

    private String address;

    private String kycStatus;

    private String tierLevel;
}
