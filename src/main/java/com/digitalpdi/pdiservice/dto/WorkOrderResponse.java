package com.digitalpdi.pdiservice.dto;

import com.digitalpdi.pdiservice.enums.PriorityLevel;
import com.digitalpdi.pdiservice.enums.WorkOrderStatus;
import com.digitalpdi.pdiservice.enums.WorkOrderType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class WorkOrderResponse {

    private Long id;

    private String workOrderNo;

    private Long machineId;

    private String machineCode;

    private Long assignedUserId;

    private String assignedUserName;

    private WorkOrderType type;

    private WorkOrderStatus status;

    private PriorityLevel priorityLevel;

    private String description;

    private String workExplanation;

    private Integer estimatedMinute;

    private Integer actualMinute;

    private LocalDateTime createdAt;

    private LocalDateTime startedAt;

    private LocalDateTime finishedAt;
}