package com.digitalpdi.pdiservice.entity;

import com.digitalpdi.pdiservice.enums.PdiItemResult;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PdiChecklistItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Hangi checklist formuna ait olduğu
    @ManyToOne
    @JoinColumn(name = "checklist_id", nullable = false)
    private PdiChecklist checklist;

    @Column(nullable = false)
    private String itemTitle;

    @Column(length = 1000)
    private String itemDescription;

    // OK, NOT_OK, NOT_APPLICABLE
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PdiItemResult result;

    // NOT_OK ise açıklama zorunlu olacak
    @Column(length = 1000)
    private String explanation;

    // PDI 2 için her maddede zorunlu olacak
    private String photoUrl;

    private boolean critical;
}