package com.digitalpdi.pdiservice.controller;

import com.digitalpdi.pdiservice.dto.ProductivitySummaryResponse;
import com.digitalpdi.pdiservice.dto.TechnicianProductivityResponse;
import com.digitalpdi.pdiservice.service.ProductivityService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productivity")
@RequiredArgsConstructor
public class ProductivityController {

    private final ProductivityService productivityService;

    @GetMapping("/summary")
    public ProductivitySummaryResponse getSummary() {
        return productivityService.getSummary();
    }

    @GetMapping("/technicians")
    public List<TechnicianProductivityResponse> getTechnicianProductivityList() {
        return productivityService.getTechnicianProductivityList();
    }

    @GetMapping("/technicians/{technicianId}")
    public TechnicianProductivityResponse getTechnicianProductivity(@PathVariable Long technicianId) {
        return productivityService.getTechnicianProductivity(technicianId);
    }
}