package com.digitalpdi.pdiservice.controller;

import com.digitalpdi.pdiservice.dto.AppUserRequest;
import com.digitalpdi.pdiservice.dto.AppUserResponse;
import com.digitalpdi.pdiservice.service.AppUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class AppUserController {

    private final AppUserService appUserService;

    @PostMapping
    public AppUserResponse create(@Valid @RequestBody AppUserRequest request) {
        return appUserService.create(request);
    }

    @GetMapping
    public List<AppUserResponse> getAll() {
        return appUserService.getAll();
    }

    @GetMapping("/{id}")
    public AppUserResponse getById(@PathVariable Long id) {
        return appUserService.getById(id);
    }

    @PutMapping("/{id}/deactivate")
    public AppUserResponse deactivate(@PathVariable Long id) {
        return appUserService.deactivate(id);
    }
}