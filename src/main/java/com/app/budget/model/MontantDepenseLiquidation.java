package com.app.budget.model;

import java.math.BigDecimal;

public class MontantDepenseLiquidation {
    private BigDecimal montantEngage;
    private BigDecimal montantLiquider;    
    private BigDecimal montantRestant;
    public BigDecimal getMontantEngage() {
        return montantEngage;
    }
    public void setMontantEngage(BigDecimal montantEngage) {
        this.montantEngage = montantEngage;
    }
    public BigDecimal getMontantLiquider() {
        return montantLiquider;
    }
    public void setMontantLiquider(BigDecimal montantLiquider) {
        this.montantLiquider = montantLiquider;
    }
    public BigDecimal getMontantRestant() {
        return montantRestant;
    }
    public void setMontantRestant(BigDecimal montantRestant) {
        this.montantRestant = montantRestant;
    }

    
    
}
