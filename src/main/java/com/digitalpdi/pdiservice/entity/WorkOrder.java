package com.digitalpdi.pdiservice.entity;

import com.digitalpdi.pdiservice.enums.PriorityLevel;
import com.digitalpdi.pdiservice.enums.WorkOrderStatus;
import com.digitalpdi.pdiservice.enums.WorkOrderType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WorkOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String workOrderNo;

    @ManyToOne
    @JoinColumn(name = "machine_id")
    private Machine machine;

    @ManyToOne
    @JoinColumn(name = "assigned_user_id")
    private AppUser assignedUser;

    @Enumerated(EnumType.STRING)
    private WorkOrderType type;

    @Enumerated(EnumType.STRING)
    private WorkOrderStatus status;

    @Enumerated(EnumType.STRING)
    private PriorityLevel priorityLevel;

    @Column(nullable = false, length = 1000)
    private String description;

    @Column(length = 1000)
    private String workExplanation;

    private Integer estimatedMinute;

    private Integer actualMinute;

    private LocalDateTime createdAt;

    private LocalDateTime startedAt;

    private LocalDateTime finishedAt;
}