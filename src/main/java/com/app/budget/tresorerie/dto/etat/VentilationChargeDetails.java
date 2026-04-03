package com.app.budget.tresorerie.dto.etat;

import java.math.BigDecimal;

import com.app.budget.domain.Projet;

public class VentilationChargeDetails {

    private Projet projet;
    private BigDecimal montant;
    public Projet getProjet() {
        return projet;
    }
    public void setProjet(Projet projet) {
        this.projet = projet;
    }
    public BigDecimal getMontant() {
        return montant;
    }
    public void setMontant(BigDecimal montant) {
        this.montant = montant;
    }

    
}