package com.digitalpdi.pdiservice.repository;

import com.digitalpdi.pdiservice.entity.WorkTimeLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WorkTimeLogRepository extends JpaRepository<WorkTimeLog, Long> {

    List<WorkTimeLog> findByWorkOrderId(Long workOrderId);
}