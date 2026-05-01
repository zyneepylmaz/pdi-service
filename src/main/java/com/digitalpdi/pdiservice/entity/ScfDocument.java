package com.digitalpdi.pdiservice.entity;

import com.digitalpdi.pdiservice.enums.DocumentStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ScfDocument {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long workOrderId;

    private Long damageReportId;

    private String documentNumber;

    private String fileUrl;

    private boolean signedByTechnician;

    private boolean signedByCustomer;

    @Enumerated(EnumType.STRING)
    private DocumentStatus status;

    private String uploadedBy;

    private LocalDateTime uploadedAt;
}