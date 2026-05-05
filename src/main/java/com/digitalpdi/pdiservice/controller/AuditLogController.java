package com.digitalpdi.pdiservice.controller;

import com.digitalpdi.pdiservice.dto.AuditLogResponse;
import com.digitalpdi.pdiservice.service.AuditLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/audit-logs")
@RequiredArgsConstructor
public class AuditLogController {

    private final AuditLogService auditLogService;

    @GetMapping
    public List<AuditLogResponse> getAll() {
        return auditLogService.getAll();
    }

    @GetMapping("/entity/{entityName}/{entityId}")
    public List<AuditLogResponse> getByEntity(
            @PathVariable String entityName,
            @PathVariable Long entityId
    ) {
        return auditLogService.getByEntity(entityName, entityId);
    }
}