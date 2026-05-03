package com.digitalpdi.pdiservice.controller;

import com.digitalpdi.pdiservice.entity.WorkOrder;
import com.digitalpdi.pdiservice.service.WorkOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/work-orders")
@RequiredArgsConstructor
public class WorkOrderController {

    private final WorkOrderService workOrderService;

    @PostMapping("/machine/{machineId}")
    public WorkOrder create(@PathVariable Long machineId, @RequestBody WorkOrder workOrder) {
        return workOrderService.create(machineId, workOrder);
    }

    @PutMapping("/{workOrderId}/assign/{userId}")
    public WorkOrder assignUser(@PathVariable Long workOrderId, @PathVariable Long userId) {
        return workOrderService.assignUser(workOrderId, userId);
    }

    @PutMapping("/{workOrderId}/start")
    public WorkOrder start(@PathVariable Long workOrderId) {
        return workOrderService.startWork(workOrderId);
    }

    @PutMapping("/{workOrderId}/complete")
    public WorkOrder complete(
            @PathVariable Long workOrderId,
            @RequestParam String workExplanation
    ) {
        return workOrderService.completeWork(workOrderId, workExplanation);
    }

    @GetMapping
    public List<WorkOrder> getAll() {
        return workOrderService.getAll();
    }

    @GetMapping("/{id}")
    public WorkOrder getById(@PathVariable Long id) {
        return workOrderService.getById(id);
    }
}
