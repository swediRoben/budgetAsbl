package com.app.budget.tresorerie.entity;
import com.app.budget.domain.PlanComptable;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "banque")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Banque {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String libelle;

    private Boolean actif;

    @OneToOne
    @JoinColumn(name = "id_compte_comptable")
    private PlanComptable compteComptable;
}