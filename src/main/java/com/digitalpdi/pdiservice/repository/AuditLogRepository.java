package com.digitalpdi.pdiservice.repository;

import com.digitalpdi.pdiservice.entity.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {

    List<AuditLog> findByEntityNameAndEntityId(String entityName, Long entityId);
}
