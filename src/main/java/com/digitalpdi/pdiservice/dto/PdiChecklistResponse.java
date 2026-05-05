package com.digitalpdi.pdiservice.dto;

import com.digitalpdi.pdiservice.enums.PdiStatus;
import com.digitalpdi.pdiservice.enums.PdiType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
public class PdiChecklistResponse {

    private Long id;

    private Long machineId;

    private String machineCode;

    private PdiType pdiType;

    private PdiStatus status;

    private String responsibleUser;

    private LocalDateTime createdAt;

    private LocalDateTime completedAt;

    private String generalNote;

    private boolean shipmentApproved;

    private List<PdiChecklistItemResponse> items;
}