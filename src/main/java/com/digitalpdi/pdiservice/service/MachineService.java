package com.digitalpdi.pdiservice.service;

import com.digitalpdi.pdiservice.entity.Machine;
import com.digitalpdi.pdiservice.enums.MachineStatus;
import com.digitalpdi.pdiservice.enums.PriorityLevel;
import com.digitalpdi.pdiservice.repository.MachineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MachineService {

    private final MachineRepository machineRepository;
    private final AuditLogService auditLogService;

    public Machine create(Machine machine) {
        machine.setStatus(MachineStatus.WAITING);

        if (machine.getPriorityLevel() == null) {
            machine.setPriorityLevel(PriorityLevel.MEDIUM);
        }

        Machine saved = machineRepository.save(machine);

        auditLogService.log(
                "Machine",
                saved.getId(),
                "MACHINE_CREATED",
                "SYSTEM",
                "Makine kaydı oluşturuldu"
        );

        return saved;
    }

    public List<Machine> getAll() {
        return machineRepository.findAll();
    }

    public Machine getById(Long id) {
        return machineRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Makine bulunamadı"));
    }
}