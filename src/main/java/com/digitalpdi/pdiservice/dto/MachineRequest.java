package com.digitalpdi.pdiservice.dto;

import com.digitalpdi.pdiservice.enums.PriorityLevel;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class MachineRequest {

    @NotBlank(message = "Makine kodu boş bırakılamaz")
    private String machineCode;

    private String model;

    private String customerName;

    private String location;

    private String salesType;

    private LocalDate shipmentDate;

    private PriorityLevel priorityLevel;
}