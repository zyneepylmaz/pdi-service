package com.digitalpdi.pdiservice.service;

import com.digitalpdi.pdiservice.entity.AppUser;
import com.digitalpdi.pdiservice.entity.Machine;
import com.digitalpdi.pdiservice.entity.WorkOrder;
import com.digitalpdi.pdiservice.enums.WorkOrderStatus;
import com.digitalpdi.pdiservice.repository.AppUserRepository;
import com.digitalpdi.pdiservice.repository.MachineRepository;
import com.digitalpdi.pdiservice.repository.WorkOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WorkOrderService {

    private final WorkOrderRepository workOrderRepository;
    private final MachineRepository machineRepository;
    private final AppUserRepository appUserRepository;
    private final AuditLogService auditLogService;

    public WorkOrder create(Long machineId, WorkOrder workOrder) {
        Machine machine = machineRepository.findById(machineId)
                .orElseThrow(() -> new RuntimeException("Makine bulunamadı"));

        workOrder.setMachine(machine);
        workOrder.setStatus(WorkOrderStatus.WAITING);
        workOrder.setCreatedAt(LocalDateTime.now());

        if (workOrder.getWorkOrderNo() == null || workOrder.getWorkOrderNo().isBlank()) {
            workOrder.setWorkOrderNo("WO-" + System.currentTimeMillis());
        }

        WorkOrder saved = workOrderRepository.save(workOrder);

        auditLogService.log(
                "WorkOrder",
                saved.getId(),
                "WORK_ORDER_CREATED",
                "SYSTEM",
                "İş emri oluşturuldu"
        );

        return saved;
    }

    public WorkOrder assignUser(Long workOrderId, Long userId) {
        WorkOrder workOrder = getById(workOrderId);

        AppUser user = appUserRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Kullanıcı bulunamadı"));

        workOrder.setAssignedUser(user);
        workOrder.setStatus(WorkOrderStatus.ASSIGNED);

        WorkOrder saved = workOrderRepository.save(workOrder);

        auditLogService.log(
                "WorkOrder",
                saved.getId(),
                "USER_ASSIGNED",
                user.getUsername(),
                "İş emrine usta atandı"
        );

        return saved;
    }

    public WorkOrder startWork(Long workOrderId) {
        WorkOrder workOrder = getById(workOrderId);

        workOrder.setStatus(WorkOrderStatus.IN_PROGRESS);
        workOrder.setStartedAt(LocalDateTime.now());

        WorkOrder saved = workOrderRepository.save(workOrder);

        auditLogService.log(
                "WorkOrder",
                saved.getId(),
                "WORK_STARTED",
                "SYSTEM",
                "İş başlatıldı"
        );

        return saved;
    }

    public WorkOrder completeWork(Long workOrderId, String workExplanation) {
        WorkOrder workOrder = getById(workOrderId);

        if (workExplanation == null || workExplanation.isBlank()) {
            throw new RuntimeException("İş açıklaması boş bırakılamaz. İş kapatılamaz.");
        }

        workOrder.setWorkExplanation(workExplanation);
        workOrder.setStatus(WorkOrderStatus.COMPLETED);
        workOrder.setFinishedAt(LocalDateTime.now());

        if (workOrder.getStartedAt() != null) {
            long minutes = Duration.between(workOrder.getStartedAt(), workOrder.getFinishedAt()).toMinutes();
            workOrder.setActualMinute((int) minutes);
        }

        WorkOrder saved = workOrderRepository.save(workOrder);

        auditLogService.log(
                "WorkOrder",
                saved.getId(),
                "WORK_COMPLETED",
                "SYSTEM",
                "İş açıklaması girilerek iş tamamlandı"
        );

        return saved;
    }

    public WorkOrder getById(Long id) {
        return workOrderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("İş emri bulunamadı"));
    }

    public List<WorkOrder> getAll() {
        return workOrderRepository.findAll();
    }
}