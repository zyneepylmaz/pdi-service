package com.digitalpdi.pdiservice.entity;

import com.digitalpdi.pdiservice.enums.MachineStatus;
import com.digitalpdi.pdiservice.enums.PriorityLevel;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Machine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String machineCode;

    private String model;

    private String customerName;

    private String location;

    private String salesType;

    private LocalDate shipmentDate;

    @Enumerated(EnumType.STRING)
    private MachineStatus status;

    @Enumerated(EnumType.STRING)
    private PriorityLevel priorityLevel;
}