package com.fincore.wallet.exception;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorResponse {

    private boolean success;
    private String message;
    private Object data;

}
