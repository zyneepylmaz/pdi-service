package com.digitalpdi.pdiservice.controller;

import com.digitalpdi.pdiservice.dto.PdiChecklistCreateRequest;
import com.digitalpdi.pdiservice.dto.PdiChecklistItemRequest;
import com.digitalpdi.pdiservice.dto.PdiChecklistResponse;
import com.digitalpdi.pdiservice.service.PdiChecklistService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pdi-checklists")
@RequiredArgsConstructor
public class PdiChecklistController {

    private final PdiChecklistService pdiChecklistService;

    @PostMapping
    public PdiChecklistResponse createChecklist(@Valid @RequestBody PdiChecklistCreateRequest request) {
        return pdiChecklistService.createChecklist(request);
    }

    @PostMapping("/{checklistId}/items")
    public PdiChecklistResponse addItem(
            @PathVariable Long checklistId,
            @Valid @RequestBody PdiChecklistItemRequest request
    ) {
        return pdiChecklistService.addItem(checklistId, request);
    }

    @PutMapping("/{checklistId}/complete")
    public PdiChecklistResponse completeChecklist(@PathVariable Long checklistId) {
        return pdiChecklistService.completeChecklist(checklistId);
    }

    @GetMapping
    public List<PdiChecklistResponse> getAll() {
        return pdiChecklistService.getAll();
    }

    @GetMapping("/{id}")
    public PdiChecklistResponse getById(@PathVariable Long id) {
        return pdiChecklistService.getById(id);
    }

    @GetMapping("/machine/{machineId}")
    public List<PdiChecklistResponse> getByMachineId(@PathVariable Long machineId) {
        return pdiChecklistService.getByMachineId(machineId);
    }
}