package com.digitalpdi.pdiservice.controller;

import com.digitalpdi.pdiservice.entity.Machine;
import com.digitalpdi.pdiservice.service.MachineService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/machines")
@RequiredArgsConstructor
public class MachineController {

    private final MachineService machineService;

    @PostMapping
    public Machine create(@RequestBody Machine machine) {
        return machineService.create(machine);
    }

    @GetMapping
    public List<Machine> getAll() {
        return machineService.getAll();
    }

    @GetMapping("/{id}")
    public Machine getById(@PathVariable Long id) {
        return machineService.getById(id);
    }
}