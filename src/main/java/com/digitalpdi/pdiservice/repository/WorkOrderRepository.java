package com.digitalpdi.pdiservice.repository;

import com.digitalpdi.pdiservice.entity.WorkOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WorkOrderRepository extends JpaRepository<WorkOrder, Long> {

    List<WorkOrder> findByAssignedUserId(Long assignedUserId);

    List<WorkOrder> findByMachineId(Long machineId);
}