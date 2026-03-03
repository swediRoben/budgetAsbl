package com.app.budget.tresorerie.dto;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import com.app.budget.constate.ModePaiement;
import com.app.budget.constate.Typemouvement;
import com.app.budget.domain.Classe;
import com.app.budget.domain.Devise; 
import com.app.budget.domain.PlanActivite;
import com.app.budget.domain.PlanComptable;
import com.app.budget.domain.SourceFinacement;
import com.app.budget.model.LiquidationDTO;
import com.app.budget.tresorerie.entity.Banque;
import com.app.budget.tresorerie.entity.CompteBancaire; 
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
    private String reference;
    private Long idExercice; 
    private Typemouvement typemouvement;
    private BigDecimal taux;
    private BigDecimal montant;
    private String objet;
    private OffsetDateTime date;

    private Long projetId;
    private Long categorieId;
    private Long banqueId;
    private Long planComptableId;
    private Long classeId;
    private Long deviseId;
    private Long compteBancaireId;
    private Long liquidationId;
    private Long planActiviteId;
    private Long sourceFinacementId;
    private ModePaiement modepaiement;
    private String numroCheque; 
    
    private Banque banque;
    private PlanComptable planComptable;
    private Classe classe;
    private Devise devise; 
    private CompteBancaire compteBancaire; 
    private PlanActivite planActivite;
    private SourceFinacement sourceFinacement;
    private LiquidationDTO liquidation;
}