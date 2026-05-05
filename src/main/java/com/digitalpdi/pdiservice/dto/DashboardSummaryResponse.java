package com.digitalpdi.pdiservice.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class DashboardSummaryResponse {

    // Machine KPIs
    private long totalMachines;
    private long pdi1Machines;
    private long pdi2Machines;
    private long readyForShipmentMachines;

    // PDI KPIs
    private long totalPdiChecklists;
    private long approvedPdiChecklists;
    private long rejectedPdiChecklists;
    private long inProgressPdiChecklists;
    private long pdi2ApprovedChecklists;
    private long pdi2RejectedChecklists;

    // Work Order / Productivity KPIs
    private long totalWorkOrders;
    private long openWorkOrders;
    private long completedWorkOrders;
    private long inProgressWorkOrders;
    private int totalActualMinutes;
    private double averageActualMinutes;

    // Supporting Damage / SCF KPIs
    private long openDamageReports;
    private long waitingCustomerApprovalDamageReports;
    private long missingOrUnsignedScfDocuments;
}