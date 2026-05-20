package com.fincore.wallet.audit.controller;

import com.fincore.wallet.audit.service.AuditService;
import com.fincore.wallet.common.response.ApiResponse;
import com.fincore.wallet.audit.dto.AuditLogResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/audits")
@RequiredArgsConstructor
public class AuditController {

    private final AuditService auditService;

    @GetMapping
    public ApiResponse<List<AuditLogResponse>> getAuditLogs(){
        return ApiResponse
                .<List<AuditLogResponse>>builder()
                .success(true)
                .message("Audit logs fetched successfully")
                .data(auditService.getAllAuditLogs())
                .build();
    }

}
