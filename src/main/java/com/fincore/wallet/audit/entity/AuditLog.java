package com.fincore.wallet.audit.entity;

import com.fincore.wallet.common.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "audit_logs")
public class AuditLog extends BaseEntity {

    @Column(nullable = false)
    private String action;

    @Column(nullable = false)
    private String performedBy;

    private String entityName;

    private String details;

    @Column(nullable = false)
    private String status;

}
