package com.digitalpdi.pdiservice.entity;

import com.digitalpdi.pdiservice.enums.PdiStatus;
import com.digitalpdi.pdiservice.enums.PdiType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PdiChecklist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Hangi makineye ait PDI kontrolü olduğunu tutar
    @ManyToOne
    @JoinColumn(name = "machine_id", nullable = false)
    private Machine machine;

    // PDI 1 mi PDI 2 mi?
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PdiType pdiType;

    // IN_PROGRESS, APPROVED, REJECTED
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PdiStatus status;

    // Kontrolü yapan kişi
    @Column(nullable = false)
    private String responsibleUser;

    private LocalDateTime createdAt;

    private LocalDateTime completedAt;

    @Column(length = 1000)
    private String generalNote;

    // PDI 2 onaylanırsa true olur
    private boolean shipmentApproved;
}