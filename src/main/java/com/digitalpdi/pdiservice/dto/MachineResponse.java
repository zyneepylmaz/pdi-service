package com.digitalpdi.pdiservice.dto;

import com.digitalpdi.pdiservice.enums.MachineStatus;
import com.digitalpdi.pdiservice.enums.PriorityLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
public class MachineResponse {

    private Long id;

    private String machineCode;

    private String model;

    private String customerName;

    private String location;

    private String salesType;

    private LocalDate shipmentDate;

    private MachineStatus status;

    private PriorityLevel priorityLevel;
}