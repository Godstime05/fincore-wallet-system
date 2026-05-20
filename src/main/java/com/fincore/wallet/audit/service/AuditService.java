package com.fincore.wallet.audit.service;

import com.fincore.wallet.audit.entity.AuditLog;
import com.fincore.wallet.audit.repository.AuditLogRepository;
import com.fincore.wallet.audit.dto.AuditLogResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuditService {

    private final AuditLogRepository auditLogRepository;
//    private final AuditLogResponse auditLogResponse;

    public void logAction(String action,
                          String performedBy,
                          String entityNAme,
                          String details,
                          String status
){
        AuditLog auditLog = new AuditLog();

        auditLog.setAction(action);
        auditLog.setPerformedBy(performedBy);
        auditLog.setEntityName(entityNAme);
        auditLog.setDetails(details);
        auditLog.setStatus(status);

        auditLogRepository.save(auditLog);
    }


    public List<AuditLogResponse> getAllAuditLogs(){
        return auditLogRepository.findAll()
                .stream()
                .map(log ->
                        AuditLogResponse.builder()
                                .action(log.getAction())
                                .performedBy(log.getPerformedBy())
                                .entityName(log.getEntityName())
                                .details(log.getDetails())
                                .status(log.getStatus())
                                .timestamp(log.getCreatedAt())
                                .build()
                                )
                .toList();

    }
}
