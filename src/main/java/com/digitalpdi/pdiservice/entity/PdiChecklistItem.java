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

    @ManyToOne
    @JoinColumn(name = "checklist_id", nullable = false)
    private PdiChecklist checklist;

    @Column(nullable = false)
    private String itemTitle;

    @Column(length = 1000)
    private String itemDescription;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PdiItemResult result;

    @Column(length = 1000)
    private String explanation;

    private String photoUrl;

    private boolean critical;
}