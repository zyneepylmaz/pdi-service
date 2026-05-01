package com.digitalpdi.pdiservice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WorkTimeLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long workOrderId;

    private Long userId;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private Integer durationMinute;

    @Column(length = 1000)
    private String note;
}