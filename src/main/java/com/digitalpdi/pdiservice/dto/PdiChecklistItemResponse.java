package com.digitalpdi.pdiservice.dto;

import com.digitalpdi.pdiservice.enums.PdiItemResult;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PdiChecklistItemResponse {

    private Long id;

    private String itemTitle;

    private String itemDescription;

    private PdiItemResult result;

    private String explanation;

    private String photoUrl;

    private boolean critical;
}