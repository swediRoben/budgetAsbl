package com.app.budget.tresorerie.entity;

import java.math.BigDecimal;

import com.app.budget.domain.Classe;
import com.app.budget.domain.PlanComptable;
import com.app.budget.domain.Projet;
import com.app.budget.domain.SourceFinacement;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ligne_comptable")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LigneComptable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "comptabilite_id")
    @JsonIgnore
    private Comptabilite ecriture;

    @Column(name = "id_projets")
    private Long idProjet;

    @Column(name = "id_classe")
    private Long idClasse;

    @Column(name = "compte_id")
    private Long idCompte;
    
    @Column(name = "id_source")
    private Long idSource; 

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "compte_id", referencedColumnName = "id", insertable = false, updatable = false)
    private PlanComptable compte;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_projets", referencedColumnName = "id", insertable = false, updatable = false)
    private Projet projet;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_classe", referencedColumnName = "id", insertable = false, updatable = false)
    private Classe classe;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_source", referencedColumnName = "id", insertable = false, updatable = false)
    private SourceFinacement source; 

    private BigDecimal debit;
    private BigDecimal credit;

    private String libelle;
    private Long devise;
}