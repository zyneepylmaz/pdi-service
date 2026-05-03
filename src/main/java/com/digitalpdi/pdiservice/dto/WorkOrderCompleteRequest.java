package com.digitalpdi.pdiservice.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WorkOrderCompleteRequest {

    @NotBlank(message = "Yapılan iş açıklaması boş bırakılamaz")
    private String workExplanation;
}