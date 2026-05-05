package com.digitalpdi.pdiservice.service;

import com.digitalpdi.pdiservice.dto.AppUserRequest;
import com.digitalpdi.pdiservice.dto.AppUserResponse;
import com.digitalpdi.pdiservice.entity.AppUser;
import com.digitalpdi.pdiservice.repository.AppUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AppUserService {

    private final AppUserRepository appUserRepository;
    private final AuditLogService auditLogService;

    public AppUserResponse create(AppUserRequest request) {
        appUserRepository.findByUsername(request.getUsername())
                .ifPresent(user -> {
                    throw new RuntimeException("Bu kullanıcı adı zaten kullanılıyor");
                });

        AppUser user = AppUser.builder()
                .fullName(request.getFullName())
                .username(request.getUsername())
                .password(request.getPassword())
                .role(request.getRole())
                .active(true)
                .createdAt(LocalDateTime.now())
                .build();

        AppUser savedUser = appUserRepository.save(user);

        auditLogService.log(
                "AppUser",
                savedUser.getId(),
                "USER_CREATED",
                "SYSTEM",
                "Yeni kullanıcı oluşturuldu: " + savedUser.getUsername()
        );

        return toResponse(savedUser);
    }

    public List<AppUserResponse> getAll() {
        return appUserRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public AppUserResponse getById(Long id) {
        AppUser user = appUserRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Kullanıcı bulunamadı"));

        return toResponse(user);
    }

    public AppUserResponse deactivate(Long id) {
        AppUser user = appUserRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Kullanıcı bulunamadı"));

        user.setActive(false);

        AppUser savedUser = appUserRepository.save(user);

        auditLogService.log(
                "AppUser",
                savedUser.getId(),
                "USER_DEACTIVATED",
                "SYSTEM",
                "Kullanıcı pasif hale getirildi: " + savedUser.getUsername()
        );

        return toResponse(savedUser);
    }

    private AppUserResponse toResponse(AppUser user) {
        return AppUserResponse.builder()
                .id(user.getId())
                .fullName(user.getFullName())
                .username(user.getUsername())
                .role(user.getRole())
                .active(user.isActive())
                .createdAt(user.getCreatedAt())
                .build();
    }
}