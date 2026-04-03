package com.app.budget.tresorerie.dto.etat;

import java.math.BigDecimal;

import com.app.budget.domain.PlanfondProjet;

public class Ressources {
    private PlanfondProjet projet;
    private BigDecimal montantRecus;
    private BigDecimal montantDepense;
    
    public PlanfondProjet getProjet() {
        return projet;
    }
    public void setProjet(PlanfondProjet projet) {
        this.projet = projet;
    }
    public BigDecimal getMontantRecus() {
        return montantRecus;
    }
    public void setMontantRecus(BigDecimal montantRecus) {
        this.montantRecus = montantRecus;
    }
    public BigDecimal getMontantDepense() {
        return montantDepense;
    }
    public void setMontantDepense(BigDecimal montantDepense) {
        this.montantDepense = montantDepense;
    }
     
    
}
