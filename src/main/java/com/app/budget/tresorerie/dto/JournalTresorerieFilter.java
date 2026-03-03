package com.app.budget.tresorerie.dto;

import java.time.OffsetDateTime;

import com.app.budget.constate.Typemouvement;

import lombok.Data;
@Data
public class JournalTresorerieFilter {

    private Long exerciceId;
    private OffsetDateTime debut;
    private OffsetDateTime fin;
    private String reference;

    private Long projetId;
    private Long categorieId;
    private Typemouvement typeMouvement;

    private Long sourceFinancementId;
    private Long banqueId;
    private Long compteBanqueId;
}