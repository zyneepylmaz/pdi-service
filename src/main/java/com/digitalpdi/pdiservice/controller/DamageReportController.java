package com.digitalpdi.pdiservice.controller;

import com.digitalpdi.pdiservice.entity.DamageReport;
import com.digitalpdi.pdiservice.service.DamageReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/damage-reports")
@RequiredArgsConstructor
public class DamageReportController {

    private final DamageReportService damageReportService;

    @PostMapping
    public DamageReport create(@RequestBody DamageReport report) {
        return damageReportService.create(report);
    }

    @PutMapping("/{id}/inform-customer")
    public DamageReport informCustomer(@PathVariable Long id) {
        return damageReportService.informCustomer(id);
    }

    @PutMapping("/{id}/customer-approve")
    public DamageReport approveCustomer(@PathVariable Long id) {
        return damageReportService.approveCustomer(id);
    }

    @PutMapping("/{id}/close")
    public DamageReport close(@PathVariable Long id) {
        return damageReportService.close(id);
    }

    @GetMapping
    public List<DamageReport> getAll() {
        return damageReportService.getAll();
    }

    @GetMapping("/{id}")
    public DamageReport getById(@PathVariable Long id) {
        return damageReportService.getById(id);
    }
}