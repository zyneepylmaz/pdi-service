package com.digitalpdi.pdiservice.repository;

import com.digitalpdi.pdiservice.entity.DamageReport;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DamageReportRepository extends JpaRepository<DamageReport, Long> {

    List<DamageReport> findByMachineId(Long machineId);

    List<DamageReport> findByWorkOrderId(Long workOrderId);
}