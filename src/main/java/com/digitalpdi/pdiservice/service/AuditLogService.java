package com.digitalpdi.pdiservice.service;

import com.digitalpdi.pdiservice.entity.AuditLog;
import com.digitalpdi.pdiservice.repository.AuditLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuditLogService {

    private final AuditLogRepository auditLogRepository;

    public void log(String entityName, Long entityId, String action, String performedBy, String description) {
        AuditLog auditLog = AuditLog.builder()
                .entityName(entityName)
                .entityId(entityId)
                .action(action)
                .performedBy(performedBy)
                .performedAt(LocalDateTime.now())
                .description(description)
                .build();

        auditLogRepository.save(auditLog);
    }
}