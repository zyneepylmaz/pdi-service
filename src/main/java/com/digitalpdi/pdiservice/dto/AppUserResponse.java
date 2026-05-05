package com.digitalpdi.pdiservice.dto;

import com.digitalpdi.pdiservice.enums.Role;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class AppUserResponse {

    private Long id;

    private String fullName;

    private String username;

    private Role role;

    private boolean active;

    private LocalDateTime createdAt;
}
