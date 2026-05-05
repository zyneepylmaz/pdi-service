package com.digitalpdi.pdiservice.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class AuditLogResponse {

    private Long id;

    private String entityName;

    private Long entityId;

    private String action;

    private String performedBy;

    private LocalDateTime performedAt;

    private String description;
}