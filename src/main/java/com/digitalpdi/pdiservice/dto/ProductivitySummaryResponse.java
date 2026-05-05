package com.digitalpdi.pdiservice.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ProductivitySummaryResponse {

    private long totalWorkOrders;

    private long completedWorkOrders;

    private long inProgressWorkOrders;

    private long waitingWorkOrders;

    private long assignedWorkOrders;

    private long cancelledWorkOrders;

    private int totalActualMinutes;

    private double averageActualMinutes;
}