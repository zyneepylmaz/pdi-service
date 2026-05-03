package com.digitalpdi.pdiservice.controller;

import com.digitalpdi.pdiservice.entity.ScfDocument;
import com.digitalpdi.pdiservice.service.ScfDocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/scf-documents")
@RequiredArgsConstructor
public class ScfDocumentController {

    private final ScfDocumentService scfDocumentService;

    @PostMapping
    public ScfDocument upload(@RequestBody ScfDocument document) {
        return scfDocumentService.upload(document);
    }

    @PutMapping("/{id}/signature")
    public ScfDocument updateSignature(
            @PathVariable Long id,
            @RequestParam boolean signedByTechnician,
            @RequestParam boolean signedByCustomer
    ) {
        return scfDocumentService.updateSignature(id, signedByTechnician, signedByCustomer);
    }

    @GetMapping
    public List<ScfDocument> getAll() {
        return scfDocumentService.getAll();
    }

    @GetMapping("/{id}")
    public ScfDocument getById(@PathVariable Long id) {
        return scfDocumentService.getById(id);
    }
}