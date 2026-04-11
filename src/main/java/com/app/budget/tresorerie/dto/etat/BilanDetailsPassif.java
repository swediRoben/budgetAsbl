package com.app.budget.tresorerie.dto.etat;

import java.math.BigDecimal;

import com.app.budget.domain.PlanComptable; 

public class BilanDetailsPassif {
  private PlanComptable planComptable;
  private BigDecimal montant;
  
  public BigDecimal getMontant() {
    return montant;
  }
  public void setMontant(BigDecimal montant) {
    this.montant = montant;
  }
  public PlanComptable getPlanComptable() {
    return planComptable;
  }
  public void setPlanComptable(PlanComptable planComptable) {
    this.planComptable = planComptable;
  } 
  
  
  
}
