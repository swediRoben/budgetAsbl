package com.app.budget.tresorerie.entity;
 
import java.math.BigDecimal;
import java.time.OffsetDateTime;

import com.app.budget.constate.ModePaiement; 
import com.app.budget.constate.Typemouvement;
import com.app.budget.domain.Classe;
import com.app.budget.domain.Devise;
import com.app.budget.domain.Liquidation;
import com.app.budget.domain.PlanActivite;
import com.app.budget.domain.PlanComptable;
import com.app.budget.domain.SourceFinacement;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Journal_tresorerie")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JournalTresorerie {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String reference;

    private Long idExercice;

    private Long projetId;
    private Long categorieId; 

    @Enumerated(EnumType.STRING)
    private Typemouvement typemouvement;
    private String numroCheque;

    @Enumerated(EnumType.STRING)
    private ModePaiement modepaiement;


    private BigDecimal taux;
    private BigDecimal montant;
    private String objet;
    private OffsetDateTime date; 

    @ManyToOne
    @JoinColumn(name = "id_banque")
    private Banque banque;

    @ManyToOne
    @JoinColumn(name = "id_comte_comptable")
    private PlanComptable planComptable;
    
    @ManyToOne
    @JoinColumn(name = "id_classe")
    private Classe classe;
    
    @ManyToOne
    @JoinColumn(name = "id_devise")
    private Devise devise; 

    @ManyToOne
    @JoinColumn(name = "id_compte_bancaire")
    private CompteBancaire compteBancaire;
    
    @ManyToOne
    @JoinColumn(name = "id_liquidation",nullable = true)
    private Liquidation liquidation;

 
    @ManyToOne
    @JoinColumn(name = "id_planActivite",nullable = true)
    private PlanActivite planActivite;
    
    @ManyToOne
    @JoinColumn(name = "id_sourceFinacement")
    private SourceFinacement sourceFinacement;
}