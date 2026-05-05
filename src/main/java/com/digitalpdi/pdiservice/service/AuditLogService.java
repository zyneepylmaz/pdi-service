package com.digitalpdi.pdiservice.service;

import com.digitalpdi.pdiservice.dto.AuditLogResponse;
import com.digitalpdi.pdiservice.entity.AuditLog;
import com.digitalpdi.pdiservice.repository.AuditLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

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

    public List<AuditLogResponse> getAll() {
        return auditLogRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public List<AuditLogResponse> getByEntity(String entityName, Long entityId) {
        return auditLogRepository.findByEntityNameAndEntityId(entityName, entityId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    private AuditLogResponse toResponse(AuditLog auditLog) {
        return AuditLogResponse.builder()
                .id(auditLog.getId())
                .entityName(auditLog.getEntityName())
                .entityId(auditLog.getEntityId())
                .action(auditLog.getAction())
                .performedBy(auditLog.getPerformedBy())
                .performedAt(auditLog.getPerformedAt())
                .description(auditLog.getDescription())
                .build();
    }
}