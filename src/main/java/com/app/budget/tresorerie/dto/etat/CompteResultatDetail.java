package com.app.budget.tresorerie.dto.etat;

import java.math.BigDecimal;

import com.app.budget.domain.Projet;
import com.app.budget.domain.SourceFinacement;

public class CompteResultatDetail {
    private Projet projet;
    private BigDecimal montant;
    private SourceFinacement sourceFinacement;

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
    public SourceFinacement getSourceFinacement() {
        return sourceFinacement;
    }
    public void setSourceFinacement(SourceFinacement sourceFinacement) {
        this.sourceFinacement = sourceFinacement;
    }
    
}
