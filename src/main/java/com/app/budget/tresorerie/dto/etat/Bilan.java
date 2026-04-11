package com.app.budget.tresorerie.dto.etat;

import java.util.ArrayList;
import java.util.List;

import com.app.budget.tresorerie.entity.Banque;

public class Bilan {
    private Banque banque;
    private List<BilanDetailsActif> detailsActif=new ArrayList<>();
    private List<BilanDetailsPassif> detailsPassif=new ArrayList<>();
    public Banque getBanque() {
        return banque;
    }
    public void setBanque(Banque banque) {
        this.banque = banque;
    }
    public List<BilanDetailsActif> getDetailsActif() {
        return detailsActif;
    }
    public void setDetailsActif(List<BilanDetailsActif> detailsActif) {
        this.detailsActif = detailsActif;
    }
    public List<BilanDetailsPassif> getDetailsPassif() {
        return detailsPassif;
    }
    public void setDetailsPassif(List<BilanDetailsPassif> detailsPassif) {
        this.detailsPassif = detailsPassif;
    }
   
    
}
