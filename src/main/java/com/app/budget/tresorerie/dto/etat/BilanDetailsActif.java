package com.app.budget.tresorerie.dto.etat;

import java.math.BigDecimal;

import com.app.budget.tresorerie.entity.CompteBancaire;

public class BilanDetailsActif {
  private CompteBancaire compteBancaire;
  private BigDecimal montant;
  public CompteBancaire getCompteBancaire() {
    return compteBancaire;
  }
  public void setCompteBancaire(CompteBancaire compteBancaire) {
    this.compteBancaire = compteBancaire;
  }
  public BigDecimal getMontant() {
    return montant;
  }
  public void setMontant(BigDecimal montant) {
    this.montant = montant;
  }   
  
}
