package com.digitalpdi.pdiservice.service;

import com.digitalpdi.pdiservice.dto.DashboardSummaryResponse;
import com.digitalpdi.pdiservice.entity.WorkOrder;
import com.digitalpdi.pdiservice.enums.DamageStatus;
import com.digitalpdi.pdiservice.enums.DocumentStatus;
import com.digitalpdi.pdiservice.enums.MachineStatus;
import com.digitalpdi.pdiservice.enums.PdiStatus;
import com.digitalpdi.pdiservice.enums.PdiType;
import com.digitalpdi.pdiservice.enums.WorkOrderStatus;
import com.digitalpdi.pdiservice.repository.DamageReportRepository;
import com.digitalpdi.pdiservice.repository.MachineRepository;
import com.digitalpdi.pdiservice.repository.PdiChecklistRepository;
import com.digitalpdi.pdiservice.repository.ScfDocumentRepository;
import com.digitalpdi.pdiservice.repository.WorkOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final MachineRepository machineRepository;
    private final PdiChecklistRepository pdiChecklistRepository;
    private final WorkOrderRepository workOrderRepository;
    private final DamageReportRepository damageReportRepository;
    private final ScfDocumentRepository scfDocumentRepository;

    public DashboardSummaryResponse getSummary() {
        long totalWorkOrders = workOrderRepository.count();
        long completedWorkOrders = workOrderRepository.countByStatus(WorkOrderStatus.COMPLETED);

        int totalActualMinutes = workOrderRepository.findAll()
                .stream()
                .filter(workOrder -> workOrder.getActualMinute() != null)
                .mapToInt(workOrder -> Math.max(workOrder.getActualMinute(), 1))
                .sum();

        double averageActualMinutes = completedWorkOrders == 0
                ? 0
                : Math.max(1, (double) totalActualMinutes / completedWorkOrders);

        long openWorkOrders =
                workOrderRepository.countByStatus(WorkOrderStatus.WAITING)
                        + workOrderRepository.countByStatus(WorkOrderStatus.ASSIGNED)
                        + workOrderRepository.countByStatus(WorkOrderStatus.IN_PROGRESS)
                        + workOrderRepository.countByStatus(WorkOrderStatus.PAUSED)
                        + workOrderRepository.countByStatus(WorkOrderStatus.WAITING_PART)
                        + workOrderRepository.countByStatus(WorkOrderStatus.WAITING_CONTROL);

        long missingOrUnsignedScfDocuments =
                scfDocumentRepository.countByStatus(DocumentStatus.MISSING_DOCUMENT)
                        + scfDocumentRepository.countByStatus(DocumentStatus.WAITING_SIGNATURE)
                        + scfDocumentRepository.countByStatus(DocumentStatus.PARTIALLY_SIGNED);

        return DashboardSummaryResponse.builder()
                // Machine KPIs
                .totalMachines(machineRepository.count())
                .pdi1Machines(machineRepository.countByStatus(MachineStatus.IN_PDI_1))
                .pdi2Machines(machineRepository.countByStatus(MachineStatus.IN_PDI_2))
                .readyForShipmentMachines(machineRepository.countByStatus(MachineStatus.READY_FOR_SHIPMENT))

                // PDI KPIs
                .totalPdiChecklists(pdiChecklistRepository.count())
                .approvedPdiChecklists(pdiChecklistRepository.countByStatus(PdiStatus.APPROVED))
                .rejectedPdiChecklists(pdiChecklistRepository.countByStatus(PdiStatus.REJECTED))
                .inProgressPdiChecklists(pdiChecklistRepository.countByStatus(PdiStatus.IN_PROGRESS))
                .pdi2ApprovedChecklists(
                        pdiChecklistRepository.countByPdiTypeAndStatus(
                                PdiType.PDI_2_FINAL_CONTROL,
                                PdiStatus.APPROVED
                        )
                )
                .pdi2RejectedChecklists(
                        pdiChecklistRepository.countByPdiTypeAndStatus(
                                PdiType.PDI_2_FINAL_CONTROL,
                                PdiStatus.REJECTED
                        )
                )

                // Work Order / Productivity KPIs
                .totalWorkOrders(totalWorkOrders)
                .openWorkOrders(openWorkOrders)
                .completedWorkOrders(completedWorkOrders)
                .inProgressWorkOrders(workOrderRepository.countByStatus(WorkOrderStatus.IN_PROGRESS))
                .totalActualMinutes(totalActualMinutes)
                .averageActualMinutes(averageActualMinutes)

                // Supporting Damage / SCF KPIs
                .openDamageReports(damageReportRepository.countByStatus(DamageStatus.OPEN))
                .waitingCustomerApprovalDamageReports(
                        damageReportRepository.countByStatus(DamageStatus.WAITING_CUSTOMER_APPROVAL)
                )
                .missingOrUnsignedScfDocuments(missingOrUnsignedScfDocuments)
                .build();
    }
}