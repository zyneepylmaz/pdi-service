package com.digitalpdi.pdiservice.service;

import com.digitalpdi.pdiservice.entity.ScfDocument;
import com.digitalpdi.pdiservice.enums.DocumentStatus;
import com.digitalpdi.pdiservice.repository.ScfDocumentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ScfDocumentService {

    private final ScfDocumentRepository scfDocumentRepository;
    private final AuditLogService auditLogService;

    public ScfDocument upload(ScfDocument document) {
        document.setUploadedAt(LocalDateTime.now());

        updateDocumentStatus(document);

        ScfDocument saved = scfDocumentRepository.save(document);

        auditLogService.log(
                "ScfDocument",
                saved.getId(),
                "SCF_DOCUMENT_UPLOADED",
                "SYSTEM",
                "SCF belgesi sisteme yüklendi"
        );

        return saved;
    }

    public ScfDocument updateSignature(Long id, boolean signedByTechnician, boolean signedByCustomer) {
        ScfDocument document = getById(id);

        document.setSignedByTechnician(signedByTechnician);
        document.setSignedByCustomer(signedByCustomer);

        updateDocumentStatus(document);

        ScfDocument saved = scfDocumentRepository.save(document);

        auditLogService.log(
                "ScfDocument",
                saved.getId(),
                "SCF_SIGNATURE_UPDATED",
                "SYSTEM",
                "SCF imza durumu güncellendi"
        );

        return saved;
    }

    private void updateDocumentStatus(ScfDocument document) {
        if (document.getFileUrl() == null || document.getFileUrl().isBlank()) {
            document.setStatus(DocumentStatus.MISSING_DOCUMENT);
        } else if (document.isSignedByCustomer() && document.isSignedByTechnician()) {
            document.setStatus(DocumentStatus.SIGNED);
        } else if (document.isSignedByCustomer() || document.isSignedByTechnician()) {
            document.setStatus(DocumentStatus.PARTIALLY_SIGNED);
        } else {
            document.setStatus(DocumentStatus.WAITING_SIGNATURE);
        }
    }

    public ScfDocument getById(Long id) {
        return scfDocumentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("SCF belgesi bulunamadı"));
    }

    public List<ScfDocument> getAll() {
        return scfDocumentRepository.findAll();
    }
}