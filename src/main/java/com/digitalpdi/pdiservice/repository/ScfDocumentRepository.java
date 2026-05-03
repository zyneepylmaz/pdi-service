package com.digitalpdi.pdiservice.repository;

import com.digitalpdi.pdiservice.entity.ScfDocument;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScfDocumentRepository extends JpaRepository<ScfDocument, Long> {

    List<ScfDocument> findByWorkOrderId(Long workOrderId);

    List<ScfDocument> findByDamageReportId(Long damageReportId);
}