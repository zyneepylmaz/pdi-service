package com.digitalpdi.pdiservice.dto;

import com.digitalpdi.pdiservice.enums.PdiType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PdiChecklistCreateRequest {

    @NotNull(message = "Makine ID boş bırakılamaz")
    private Long machineId;

    @NotNull(message = "PDI türü boş bırakılamaz")
    private PdiType pdiType;

    @NotBlank(message = "Sorumlu kullanıcı boş bırakılamaz")
    private String responsibleUser;

    private String generalNote;
}