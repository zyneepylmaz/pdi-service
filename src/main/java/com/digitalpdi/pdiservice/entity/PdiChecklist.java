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

    @ManyToOne
    @JoinColumn(name = "machine_id", nullable = false)
    private Machine machine;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PdiType pdiType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PdiStatus status;

    @Column(nullable = false)
    private String responsibleUser;

    private LocalDateTime createdAt;

    private LocalDateTime completedAt;

    @Column(length = 1000)
    private String generalNote;

    private boolean shipmentApproved;
}