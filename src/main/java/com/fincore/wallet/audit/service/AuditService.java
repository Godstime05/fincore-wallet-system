package com.fincore.wallet.audit.service;

import com.fincore.wallet.audit.entity.AuditLog;
import com.fincore.wallet.audit.repository.AuditLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuditService {

    private final AuditLogRepository auditLogRepository;

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
}
