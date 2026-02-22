package com.app.budget.tresorerie.dto;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import com.app.budget.constate.Typemouvement;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JournalTresorerieDto {

    private Long id;
    private String numero;
    private Long idExercice;
    private Boolean typebudget;
    private Typemouvement typemouvement;
    private BigDecimal taux;
    private BigDecimal montant;
    private String objet;
    private OffsetDateTime date;

    private Long banqueId;
    private Long planComptableId;
    private Long classeId;
    private Long deviseId;
    private Long compteBancaireId;
    private Long liquidationId;
    private Long planActiviteId;
    private Long sourceFinacementId;
}