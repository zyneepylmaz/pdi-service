package com.digitalpdi.pdiservice.service;

import com.digitalpdi.pdiservice.dto.ProductivitySummaryResponse;
import com.digitalpdi.pdiservice.dto.TechnicianProductivityResponse;
import com.digitalpdi.pdiservice.entity.AppUser;
import com.digitalpdi.pdiservice.entity.WorkOrder;
import com.digitalpdi.pdiservice.enums.Role;
import com.digitalpdi.pdiservice.enums.WorkOrderStatus;
import com.digitalpdi.pdiservice.repository.AppUserRepository;
import com.digitalpdi.pdiservice.repository.WorkOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductivityService {

    private final WorkOrderRepository workOrderRepository;
    private final AppUserRepository appUserRepository;

    public ProductivitySummaryResponse getSummary() {
        List<WorkOrder> workOrders = workOrderRepository.findAll();

        long totalWorkOrders = workOrders.size();

        long completedWorkOrders = workOrders.stream()
                .filter(workOrder -> workOrder.getStatus() == WorkOrderStatus.COMPLETED)
                .count();

        long inProgressWorkOrders = workOrders.stream()
                .filter(workOrder -> workOrder.getStatus() == WorkOrderStatus.IN_PROGRESS)
                .count();

        long waitingWorkOrders = workOrders.stream()
                .filter(workOrder -> workOrder.getStatus() == WorkOrderStatus.WAITING)
                .count();

        long assignedWorkOrders = workOrders.stream()
                .filter(workOrder -> workOrder.getStatus() == WorkOrderStatus.ASSIGNED)
                .count();

        long cancelledWorkOrders = workOrders.stream()
                .filter(workOrder -> workOrder.getStatus() == WorkOrderStatus.CANCELLED)
                .count();

        int totalActualMinutes = workOrders.stream()
                .filter(workOrder -> workOrder.getActualMinute() != null)
                .mapToInt(WorkOrder::getActualMinute)
                .sum();

        double averageActualMinutes = completedWorkOrders == 0
                ? 0
                : (double) totalActualMinutes / completedWorkOrders;

        return ProductivitySummaryResponse.builder()
                .totalWorkOrders(totalWorkOrders)
                .completedWorkOrders(completedWorkOrders)
                .inProgressWorkOrders(inProgressWorkOrders)
                .waitingWorkOrders(waitingWorkOrders)
                .assignedWorkOrders(assignedWorkOrders)
                .cancelledWorkOrders(cancelledWorkOrders)
                .totalActualMinutes(totalActualMinutes)
                .averageActualMinutes(averageActualMinutes)
                .build();
    }

    public List<TechnicianProductivityResponse> getTechnicianProductivityList() {
        List<AppUser> technicians = appUserRepository.findAll()
                .stream()
                .filter(user -> user.getRole() == Role.TECHNICIAN)
                .toList();

        return technicians.stream()
                .map(this::calculateTechnicianProductivity)
                .toList();
    }

    public TechnicianProductivityResponse getTechnicianProductivity(Long technicianId) {
        AppUser technician = appUserRepository.findById(technicianId)
                .orElseThrow(() -> new RuntimeException("Usta bulunamadı"));

        return calculateTechnicianProductivity(technician);
    }

    private TechnicianProductivityResponse calculateTechnicianProductivity(AppUser technician) {
        List<WorkOrder> assignedWorkOrders = workOrderRepository.findByAssignedUserId(technician.getId());

        long assignedCount = assignedWorkOrders.size();

        long completedCount = assignedWorkOrders.stream()
                .filter(workOrder -> workOrder.getStatus() == WorkOrderStatus.COMPLETED)
                .count();

        long inProgressCount = assignedWorkOrders.stream()
                .filter(workOrder -> workOrder.getStatus() == WorkOrderStatus.IN_PROGRESS)
                .count();

        int totalActualMinutes = assignedWorkOrders.stream()
                .filter(workOrder -> workOrder.getActualMinute() != null)
                .mapToInt(WorkOrder::getActualMinute)
                .sum();

        double averageActualMinutes = completedCount == 0
                ? 0
                : (double) totalActualMinutes / completedCount;

        return TechnicianProductivityResponse.builder()
                .technicianId(technician.getId())
                .technicianName(technician.getFullName())
                .username(technician.getUsername())
                .assignedWorkOrderCount(assignedCount)
                .completedWorkOrderCount(completedCount)
                .inProgressWorkOrderCount(inProgressCount)
                .totalActualMinutes(totalActualMinutes)
                .averageActualMinutes(averageActualMinutes)
                .build();
    }
}