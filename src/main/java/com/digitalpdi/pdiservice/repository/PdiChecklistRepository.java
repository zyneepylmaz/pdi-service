package com.digitalpdi.pdiservice.repository;

import com.digitalpdi.pdiservice.entity.PdiChecklist;
import com.digitalpdi.pdiservice.enums.PdiStatus;
import com.digitalpdi.pdiservice.enums.PdiType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PdiChecklistRepository extends JpaRepository<PdiChecklist, Long> {

    List<PdiChecklist> findByMachineId(Long machineId);

    List<PdiChecklist> findByPdiType(PdiType pdiType);

    List<PdiChecklist> findByStatus(PdiStatus status);
}