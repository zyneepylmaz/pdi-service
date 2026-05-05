package com.digitalpdi.pdiservice.dto;

import com.digitalpdi.pdiservice.enums.PdiItemResult;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PdiChecklistItemRequest {

    @NotBlank(message = "Kontrol maddesi başlığı boş bırakılamaz")
    private String itemTitle;

    private String itemDescription;

    @NotNull(message = "Kontrol sonucu boş bırakılamaz")
    private PdiItemResult result;

    private String explanation;

    private String photoUrl;

    private boolean critical;
}