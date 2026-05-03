package com.digitalpdi.pdiservice.service;

import com.digitalpdi.pdiservice.entity.DamageReport;
import com.digitalpdi.pdiservice.enums.DamageStatus;
import com.digitalpdi.pdiservice.repository.DamageReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DamageReportService {

    private final DamageReportRepository damageReportRepository;
    private final AuditLogService auditLogService;

    public DamageReport create(DamageReport report) {
        report.setStatus(DamageStatus.OPEN);
        report.setDetectedAt(LocalDateTime.now());
        report.setCustomerInformed(false);
        report.setCustomerApproved(false);

        DamageReport saved = damageReportRepository.save(report);

        auditLogService.log(
                "DamageReport",
                saved.getId(),
                "DAMAGE_CREATED",
                "SYSTEM",
                "Hasar kaydı oluşturuldu"
        );

        return saved;
    }

    public DamageReport informCustomer(Long id) {
        DamageReport report = getById(id);

        report.setCustomerInformed(true);
        report.setStatus(DamageStatus.WAITING_CUSTOMER_APPROVAL);

        DamageReport saved = damageReportRepository.save(report);

        auditLogService.log(
                "DamageReport",
                saved.getId(),
                "CUSTOMER_INFORMED",
                "SYSTEM",
                "Müşteri hasar hakkında bilgilendirildi"
        );

        return saved;
    }

    public DamageReport approveCustomer(Long id) {
        DamageReport report = getById(id);

        report.setCustomerApproved(true);
        report.setCustomerApprovalDate(LocalDateTime.now());
        report.setStatus(DamageStatus.APPROVED);

        DamageReport saved = damageReportRepository.save(report);

        auditLogService.log(
                "DamageReport",
                saved.getId(),
                "CUSTOMER_APPROVED",
                "SYSTEM",
                "Müşteri hasar işlemini onayladı"
        );

        return saved;
    }

    public DamageReport close(Long id) {
        DamageReport report = getById(id);

        if (!report.isCustomerApproved()) {
            throw new RuntimeException("Müşteri onayı olmadan hasar kaydı kapatılamaz.");
        }

        report.setStatus(DamageStatus.CLOSED);

        DamageReport saved = damageReportRepository.save(report);

        auditLogService.log(
                "DamageReport",
                saved.getId(),
                "DAMAGE_CLOSED",
                "SYSTEM",
                "Hasar kaydı kapatıldı"
        );

        return saved;
    }

    public DamageReport getById(Long id) {
        return damageReportRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Hasar kaydı bulunamadı"));
    }

    public List<DamageReport> getAll() {
        return damageReportRepository.findAll();
    }
}