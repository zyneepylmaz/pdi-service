package com.digitalpdi.pdiservice.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class TechnicianProductivityResponse {

    private Long technicianId;

    private String technicianName;

    private String username;

    private long assignedWorkOrderCount;

    private long completedWorkOrderCount;

    private long inProgressWorkOrderCount;

    private int totalActualMinutes;

    private double averageActualMinutes;
}