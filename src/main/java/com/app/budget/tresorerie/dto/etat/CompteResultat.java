package com.app.budget.tresorerie.dto.etat;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.app.budget.domain.Classe;

public class CompteResultat {
    private Classe classe;
    private BigDecimal montant;
    private List<CompteResultatDetail> projetDetails=new ArrayList<>();
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
    
    public List<CompteResultatDetail> getProjetDetails() {
        return projetDetails;
    }
    public void setProjetDetails(List<CompteResultatDetail> projetDetails) {
        this.projetDetails = projetDetails;
    }
    
    
}
