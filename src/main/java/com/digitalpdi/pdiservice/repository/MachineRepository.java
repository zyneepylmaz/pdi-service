package com.digitalpdi.pdiservice.repository;

import com.digitalpdi.pdiservice.entity.Machine;
import com.digitalpdi.pdiservice.enums.MachineStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MachineRepository extends JpaRepository<Machine, Long> {

    Optional<Machine> findByMachineCode(String machineCode);

    long countByStatus(MachineStatus status);
}