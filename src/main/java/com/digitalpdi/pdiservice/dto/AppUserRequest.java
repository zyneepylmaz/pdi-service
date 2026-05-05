package com.digitalpdi.pdiservice.dto;

import com.digitalpdi.pdiservice.enums.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AppUserRequest {

    @NotBlank(message = "Ad soyad boş bırakılamaz")
    private String fullName;

    @NotBlank(message = "Kullanıcı adı boş bırakılamaz")
    private String username;

    @NotBlank(message = "Şifre boş bırakılamaz")
    private String password;

    @NotNull(message = "Rol boş bırakılamaz")
    private Role role;
}