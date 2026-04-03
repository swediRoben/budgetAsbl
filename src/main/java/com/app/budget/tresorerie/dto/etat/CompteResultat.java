package com.app.budget.tresorerie.dto.etat;

import java.math.BigDecimal;

import com.app.budget.domain.Classe;

public class CompteResultat {
    private Classe classe;
    private BigDecimal montant;
    public Classe getClasse() {
        return classe;
    }
    public void setClasse(Classe classe) {
        this.classe = classe;
    }
    public BigDecimal getMontant() {
        return montant;
    }
    public void setMontant(BigDecimal montant) {
        this.montant = montant;
    }
    
}
