package com.app.budget.tresorerie.entity;

import java.math.BigDecimal;

import com.app.budget.domain.PlanComptable;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
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

    @ManyToOne
    @JoinColumn(name = "compte_id")
    private PlanComptable compte;

    private BigDecimal debit;
    private BigDecimal credit;

    private String libelle;   
    private Long devise;  
}