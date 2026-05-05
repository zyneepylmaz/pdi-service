package com.digitalpdi.pdiservice.service;

import com.digitalpdi.pdiservice.dto.PdiChecklistCreateRequest;
import com.digitalpdi.pdiservice.dto.PdiChecklistItemRequest;
import com.digitalpdi.pdiservice.dto.PdiChecklistItemResponse;
import com.digitalpdi.pdiservice.dto.PdiChecklistResponse;
import com.digitalpdi.pdiservice.entity.Machine;
import com.digitalpdi.pdiservice.entity.PdiChecklist;
import com.digitalpdi.pdiservice.entity.PdiChecklistItem;
import com.digitalpdi.pdiservice.enums.MachineStatus;
import com.digitalpdi.pdiservice.enums.PdiItemResult;
import com.digitalpdi.pdiservice.enums.PdiStatus;
import com.digitalpdi.pdiservice.enums.PdiType;
import com.digitalpdi.pdiservice.repository.MachineRepository;
import com.digitalpdi.pdiservice.repository.PdiChecklistItemRepository;
import com.digitalpdi.pdiservice.repository.PdiChecklistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PdiChecklistService {

    private final PdiChecklistRepository pdiChecklistRepository;
    private final PdiChecklistItemRepository pdiChecklistItemRepository;
    private final MachineRepository machineRepository;
    private final AuditLogService auditLogService;

    public PdiChecklistResponse createChecklist(PdiChecklistCreateRequest request) {
        Machine machine = machineRepository.findById(request.getMachineId())
                .orElseThrow(() -> new RuntimeException("Makine bulunamadı"));

        PdiChecklist checklist = PdiChecklist.builder()
                .machine(machine)
                .pdiType(request.getPdiType())
                .status(PdiStatus.IN_PROGRESS)
                .responsibleUser(request.getResponsibleUser())
                .createdAt(LocalDateTime.now())
                .generalNote(request.getGeneralNote())
                .shipmentApproved(false)
                .build();

        PdiChecklist savedChecklist = pdiChecklistRepository.save(checklist);

        if (request.getPdiType() == PdiType.PDI_1_PREPARATION) {
            machine.setStatus(MachineStatus.IN_PDI_1);
        }

        if (request.getPdiType() == PdiType.PDI_2_FINAL_CONTROL) {
            machine.setStatus(MachineStatus.IN_PDI_2);
        }

        machineRepository.save(machine);

        auditLogService.log(
                "PdiChecklist",
                savedChecklist.getId(),
                "PDI_CHECKLIST_CREATED",
                request.getResponsibleUser(),
                request.getPdiType() + " checklist oluşturuldu"
        );

        return toResponse(savedChecklist);
    }

    public PdiChecklistResponse addItem(Long checklistId, PdiChecklistItemRequest request) {
        PdiChecklist checklist = getChecklistEntity(checklistId);

        validateNewItemRules(checklist, request);

        PdiChecklistItem item = PdiChecklistItem.builder()
                .checklist(checklist)
                .itemTitle(request.getItemTitle())
                .itemDescription(request.getItemDescription())
                .result(request.getResult())
                .explanation(request.getExplanation())
                .photoUrl(request.getPhotoUrl())
                .critical(request.isCritical())
                .build();

        pdiChecklistItemRepository.save(item);

        auditLogService.log(
                "PdiChecklist",
                checklist.getId(),
                "PDI_ITEM_ADDED",
                checklist.getResponsibleUser(),
                "PDI maddesi eklendi: " + request.getItemTitle()
        );

        return toResponse(checklist);
    }

    public PdiChecklistResponse completeChecklist(Long checklistId) {
        PdiChecklist checklist = getChecklistEntity(checklistId);

        List<PdiChecklistItem> items = pdiChecklistItemRepository.findByChecklistId(checklistId);

        if (items.isEmpty()) {
            throw new RuntimeException("PDI checklist maddesi olmadan tamamlanamaz.");
        }

        for (PdiChecklistItem item : items) {
            validateExistingItemRules(checklist, item);
        }

        boolean hasNotOkItem = items.stream()
                .anyMatch(item -> item.getResult() == PdiItemResult.NOT_OK);

        checklist.setCompletedAt(LocalDateTime.now());

        if (hasNotOkItem) {
            checklist.setStatus(PdiStatus.REJECTED);
            checklist.setShipmentApproved(false);
        } else {
            checklist.setStatus(PdiStatus.APPROVED);

            if (checklist.getPdiType() == PdiType.PDI_2_FINAL_CONTROL) {
                checklist.setShipmentApproved(true);

                Machine machine = checklist.getMachine();
                machine.setStatus(MachineStatus.READY_FOR_SHIPMENT);
                machineRepository.save(machine);
            }
        }

        PdiChecklist savedChecklist = pdiChecklistRepository.save(checklist);

        auditLogService.log(
                "PdiChecklist",
                savedChecklist.getId(),
                "PDI_CHECKLIST_COMPLETED",
                savedChecklist.getResponsibleUser(),
                "PDI checklist tamamlandı. Sonuç: " + savedChecklist.getStatus()
        );

        return toResponse(savedChecklist);
    }

    public List<PdiChecklistResponse> getAll() {
        return pdiChecklistRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public PdiChecklistResponse getById(Long id) {
        return toResponse(getChecklistEntity(id));
    }

    public List<PdiChecklistResponse> getByMachineId(Long machineId) {
        return pdiChecklistRepository.findByMachineId(machineId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    private PdiChecklist getChecklistEntity(Long id) {
        return pdiChecklistRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("PDI checklist bulunamadı"));
    }

    private void validateNewItemRules(PdiChecklist checklist, PdiChecklistItemRequest request) {
        if (checklist.getPdiType() == PdiType.PDI_2_FINAL_CONTROL) {
            if (request.getPhotoUrl() == null || request.getPhotoUrl().isBlank()) {
                throw new RuntimeException("PDI 2 kontrolünde her madde için fotoğraf zorunludur.");
            }
        }

        if (request.getResult() == PdiItemResult.NOT_OK) {
            if (request.getExplanation() == null || request.getExplanation().isBlank()) {
                throw new RuntimeException("Uygunsuz PDI maddesi için açıklama zorunludur.");
            }
        }
    }

    private void validateExistingItemRules(PdiChecklist checklist, PdiChecklistItem item) {
        if (checklist.getPdiType() == PdiType.PDI_2_FINAL_CONTROL) {
            if (item.getPhotoUrl() == null || item.getPhotoUrl().isBlank()) {
                throw new RuntimeException("PDI 2 tamamlanamaz. Fotoğrafı eksik madde: " + item.getItemTitle());
            }
        }

        if (item.getResult() == PdiItemResult.NOT_OK) {
            if (item.getExplanation() == null || item.getExplanation().isBlank()) {
                throw new RuntimeException("PDI tamamlanamaz. Açıklaması eksik uygunsuz madde: " + item.getItemTitle());
            }
        }
    }

    private PdiChecklistResponse toResponse(PdiChecklist checklist) {
        List<PdiChecklistItemResponse> itemResponses =
                pdiChecklistItemRepository.findByChecklistId(checklist.getId())
                        .stream()
                        .map(this::toItemResponse)
                        .toList();

        return PdiChecklistResponse.builder()
                .id(checklist.getId())
                .machineId(checklist.getMachine().getId())
                .machineCode(checklist.getMachine().getMachineCode())
                .pdiType(checklist.getPdiType())
                .status(checklist.getStatus())
                .responsibleUser(checklist.getResponsibleUser())
                .createdAt(checklist.getCreatedAt())
                .completedAt(checklist.getCompletedAt())
                .generalNote(checklist.getGeneralNote())
                .shipmentApproved(checklist.isShipmentApproved())
                .items(itemResponses)
                .build();
    }

    private PdiChecklistItemResponse toItemResponse(PdiChecklistItem item) {
        return PdiChecklistItemResponse.builder()
                .id(item.getId())
                .itemTitle(item.getItemTitle())
                .itemDescription(item.getItemDescription())
                .result(item.getResult())
                .explanation(item.getExplanation())
                .photoUrl(item.getPhotoUrl())
                .critical(item.isCritical())
                .build();
    }
}