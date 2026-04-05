package com.app.budget.model;

import java.math.BigDecimal;

public class MontantDepenseEngagement {
    private BigDecimal montantVote;    
    private BigDecimal montantEngage;
    private BigDecimal montantRestant;
    
    public BigDecimal getMontantVote() {
        return montantVote;
    }
    public void setMontantVote(BigDecimal montantVote) {
        this.montantVote = montantVote;
    }
    public BigDecimal getMontantEngage() {
        return montantEngage;
    }
    public void setMontantEngage(BigDecimal montantEngage) {
        this.montantEngage = montantEngage;
    }
    public BigDecimal getMontantRestant() {
        return montantRestant;
    }
    public void setMontantRestant(BigDecimal montantRestant) {
        this.montantRestant = montantRestant;
    }

    

}
