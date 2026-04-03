package com.app.budget.tresorerie.dto.etat;

import java.math.BigDecimal;

import com.app.budget.domain.Projet;

public class Ventilation {
    private Projet projet;
    private BigDecimal banque;
    private BigDecimal caisse;
    public Projet getProjet() {
        return projet;
    }
    public void setProjet(Projet projet) {
        this.projet = projet;
    }
    public BigDecimal getBanque() {
        return banque;
    }
    public void setBanque(BigDecimal banque) {
        this.banque = banque;
    }
    public BigDecimal getCaisse() {
        return caisse;
    }
    public void setCaisse(BigDecimal caisse) {
        this.caisse = caisse;
    }
    
}
