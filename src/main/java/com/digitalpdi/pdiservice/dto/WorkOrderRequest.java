package com.digitalpdi.pdiservice.dto;

import com.digitalpdi.pdiservice.enums.PriorityLevel;
import com.digitalpdi.pdiservice.enums.WorkOrderType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WorkOrderRequest {

    @NotNull(message = "İş tipi boş bırakılamaz")
    private WorkOrderType type;

    @NotNull(message = "Öncelik seviyesi boş bırakılamaz")
    private PriorityLevel priorityLevel;

    @NotBlank(message = "İş emri açıklaması boş bırakılamaz")
    private String description;

    private Integer estimatedMinute;
}