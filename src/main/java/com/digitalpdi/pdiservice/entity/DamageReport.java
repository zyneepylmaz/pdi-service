package com.digitalpdi.pdiservice.entity;

import com.digitalpdi.pdiservice.enums.DamageSeverity;
import com.digitalpdi.pdiservice.enums.DamageStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DamageReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long machineId;

    private Long workOrderId;

    @Column(nullable = false, length = 1000)
    private String damageDescription;

    @Column(length = 1000)
    private String rootCause;

    @Column(length = 1000)
    private String correctiveAction;

    @Enumerated(EnumType.STRING)
    private DamageSeverity severity;

    @Enumerated(EnumType.STRING)
    private DamageStatus status;

    private String detectedBy;

    private LocalDateTime detectedAt;

    private boolean customerInformed;

    private boolean customerApproved;

    private LocalDateTime customerApprovalDate;

    private BigDecimal estimatedCost;

    private BigDecimal actualCost;

    private boolean customerApprovedCost;
}