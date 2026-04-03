package com.app.budget.tresorerie.dto.etat;

import java.util.ArrayList;
import java.util.List;

import com.app.budget.domain.Classe;

public class VentilationCharge {
    private Classe classe;
    private List<VentilationChargeDetails> details=new ArrayList<>();
    public Classe getClasse() {
        return classe;
    }
    public void setClasse(Classe classe) {
        this.classe = classe;
    }
    public List<VentilationChargeDetails> getDetails() {
        return details;
    }
    public void setDetails(List<VentilationChargeDetails> details) {
        this.details = details;
    }
    
}
