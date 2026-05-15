package com.fincore.wallet.auth.dto;

import lombok.Data;

@Data
public class LoginRequest {

    private String email;
    private String password;
}
