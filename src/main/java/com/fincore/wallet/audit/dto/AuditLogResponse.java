package com.fincore.wallet.audit.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class AuditLogResponse {

    private String action;
    private String performedBy;
    private String entityName;
    private String details;
    private String status;
    private LocalDateTime timestamp;

}
