package com.digitalpdi.pdiservice.repository;

import com.digitalpdi.pdiservice.entity.PdiChecklistItem;
import com.digitalpdi.pdiservice.enums.PdiItemResult;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PdiChecklistItemRepository extends JpaRepository<PdiChecklistItem, Long> {

    List<PdiChecklistItem> findByChecklistId(Long checklistId);

    List<PdiChecklistItem> findByChecklistIdAndResult(Long checklistId, PdiItemResult result);
}